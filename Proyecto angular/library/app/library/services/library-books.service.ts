import { Injectable } from "@angular/core"
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, forkJoin, map, of, switchMap} from "rxjs";
import { BookDetail } from "../components/library-model/library-book-detail";
import { Router } from "@angular/router";
import { ApiBookResponse } from "../components/library-model/library-api-book-response";
import { ApiAuthorResponse } from "../components/library-model/library-api-author-response";
import { AuthorDetail } from "../components/library-model/library-auhtor-detail";
import { ApiWorksResponse } from "../components/library-model/library-api-works-response";
import { Card } from "../components/library-model/library-card";

@Injectable({
  providedIn: 'root'
})

export class BooksService{

  public allBooks: BookDetail[] = [];

  constructor(private http:HttpClient, private router: Router){
    if(localStorage.getItem('allBooks') !== null) this.allBooks = JSON.parse(localStorage.getItem('allBooks')!);
  }

  private error:HttpErrorResponse = new HttpErrorResponse({});

  //Services for main-page
  updateLists(bookChanged:Card):void{
    let bookItem = this.allBooks.find(b => b.olid === bookChanged.olid);
    if(bookItem){
      bookItem.fav = !bookItem.fav;
      bookChanged.buttons![0].clicked = bookItem.fav;
    }
    else{
      bookChanged.buttons![0].clicked = true;
      this.allBooks.push(this.itemToBook(bookChanged));
    }

    localStorage.setItem('allBooks', JSON.stringify(this.allBooks));
  }

  addBook(bookChanged:Card):void{
    let bookInList = this.allBooks.find(b => b.olid === bookChanged.olid);
    if(bookInList){
      bookInList.fav = undefined;
      bookChanged.buttons![0].clicked = undefined;
      bookChanged.buttons![1].clicked = false;
      this.allBooks = this.allBooks.filter(b => b.olid !== bookChanged.olid);
    }else{
      bookChanged.buttons![0].clicked = false;
      bookChanged.buttons![1].clicked = true;
      this.allBooks.push(this.itemToBook(bookChanged));
    }

    localStorage.setItem('allBooks', JSON.stringify(this.allBooks));
  }

  get getFavList():BookDetail[]{
    return this.allBooks.filter(book => book.fav);
  }

  get getNoFavList():BookDetail[]{
    return this.allBooks.filter(book => !book.fav);
  }

  get getFavItems():Card[]{
    return this.getFavList.map(book => this.bookToItem(book));
  }

  get getNoFavItems():Card[]{
    return this.getNoFavList.map(book => this.bookToItem(book));
  }


  //Services for bookDetailComponent
  getBookFromOlid(olid: string, type: string): Observable<BookDetail>{
    if(type.length === 0) type = 'books';
    return this.http.get<Partial<ApiBookResponse>>(`https://openlibrary.org/${type}/${olid}.json`)
    .pipe(
      map(response => {
        return this.setDetailInfo(olid, response);
      }),
      switchMap((bookDetail: BookDetail) => {
        if (bookDetail.authorList.length) {
          const authorsRequest: Observable<BookDetail>[] = bookDetail.authorList.map(author => {
            return this.getAuthorFromOlid(author.olid)
            .pipe(
              map((authorResponse: Partial<ApiAuthorResponse>) => {
                author.name = authorResponse.name ?? '';
                return bookDetail;
              })
            );
          })
          return forkJoin(authorsRequest).pipe( map(() => bookDetail));
        }
        return of(bookDetail);
      })
    )
  }

  getAuthorFromOlid(authorOlid: string): Observable<Partial<ApiAuthorResponse>>{
    return this.http.get<Partial<ApiAuthorResponse>>(`https://openlibrary.org/authors/${authorOlid}.json`)
    .pipe(
      map(response => response)
    );
  }

  getAuthorBooks(authorOlid: string): Observable<BookDetail[]>{
    return this.http.get<Partial<ApiWorksResponse>>(`https://openlibrary.org/authors/${authorOlid}/works.json`)
    .pipe(
      switchMap(response => {
        if(!response.entries) return  of ([]);
        if(response.entries.length > 6) response.entries = response.entries.slice(0,6);
        const bookRequests: Observable<BookDetail>[] = response.entries.map(work => this.getBookFromOlid(work.key.split("/works/")[1], 'works'));
        return forkJoin(bookRequests);
      })
    );
  }

  setDetailInfo(olid: string, data: any): BookDetail{
    let bookDetail: BookDetail = {
      olid: olid,
      imageUrl:`https://covers.openlibrary.org/b/olid/${olid}.jpg`,
      title: data.title,
      authorList: [],
      date: '',
      fav: undefined,
      type: 'books',
      subtitle: '',
      description: ''
    };

    if(data.publish_date) bookDetail.date = new Date(data.publish_date).getFullYear().toString();

    bookDetail.subtitle = data.subtitle;
    bookDetail.description = data.description?.value;

    const bookFound = this.allBooks.find(book => book.olid === bookDetail.olid);
    if(bookFound) bookDetail.fav = bookFound?.fav;

    if(data.type.key && data.type.key.split("/type/")[1] === 'work') {
      bookDetail.type = 'works';

      data.authors?.slice(0, 3).forEach((author: {author: {key: string}}) => bookDetail.authorList.push({olid: author.author.key.split("/authors/")[1], name: ''}));
      if(data.covers) bookDetail.imageUrl = `https://covers.openlibrary.org/b/id/${data.covers[0]}.jpg`;
    }
    else data.authors?.slice(0, 3).forEach((author: {key: string}) => bookDetail.authorList.push({olid: author.key.split("/authors/")[1], name: ''}));


    return bookDetail;
  }

  setAuthorInfo(authorOlid: string): Observable<AuthorDetail>{
    return this.getAuthorFromOlid(authorOlid)
    .pipe(
      switchMap((response: Partial<ApiAuthorResponse>) => {
       return of ({
          olid:            authorOlid,
          name:            response.name,
          alternate_names: response.alternate_names?.slice(0, 3),
          birth_date:      response.birth_date,
          death_date:      response.death_date,
          bio:             (typeof response.bio === 'string') ? response.bio : '',
          photo:           response.photos? `https://covers.openlibrary.org/a/id/${response.photos[0]}-M.jpg` : '',
          books:           [],
        });
      })
    );
  }

  //Services for errors
  get getError(){
    return this.error;
  }

  set setError(error:HttpErrorResponse){
    this.error = error;
    this.router.navigate(['/error'])
  }

  //Service for search
  searchBooksApi(url: string, input: string): Observable<string[]>{
    return this.http.get(`https://openlibrary.org/search.json?${url}=${encodeURIComponent(input)}`)
    .pipe(
      map((response:any) => {
        let bookOlidList: string[] = [];
        for(let i=0; i<6; i++) bookOlidList.push(response.docs[i].edition_key[0])
        return bookOlidList;
      })
    );
  }

  searchAuthorsApi(input: string): Observable<string[]>{
    return this.http.get(`https://openlibrary.org/search/authors.json?q=${encodeURIComponent(input)}`)
    .pipe(
      map((response:any) => {
        let authorsOlidList: string[] = [];
        for(let i=0; i<6; i++) authorsOlidList.push(response.docs[i].key);
        return authorsOlidList;
      })
    );
  }

  searchBooks(url: string, input: string): Observable<BookDetail[]>{
    return this.searchBooksApi(url, input)
    .pipe(
      switchMap((olidList:string[]) => {
        const bookRequests: Observable<BookDetail>[] = olidList.map(olid => this.getBookFromOlid(olid, 'books'));
        return forkJoin(bookRequests);
      })
    )
  }

  searchAuhtors(input: string): Observable<AuthorDetail[]>{
    return this.searchAuthorsApi(input)
    .pipe(
      switchMap((olidList:string[]) => {
        const authorRequests: Observable<AuthorDetail>[] = olidList.map(olid => this.setAuthorInfo(olid));
        return forkJoin(authorRequests);
      })
    )
  }

  //New structure
  bookToItem(book: BookDetail): Card{
    let auhtorLinks: {name: string, route: string}[] = [];
    book.authorList?.forEach(author => auhtorLinks.push({name:author.name, route: `/author/${author.olid}`}));
    if(!book.type) book.type = 'books';
    return{
      olid: book.olid,
      url: `/${book.type.slice(0,-1)}/${book.olid}`,
      buttons: [
        {name: '&#x2764', clicked: book.fav},
        {name: '+', clicked: book.fav === undefined ? false : true}
      ],
      imageUrl: book.imageUrl,
      title: book.title,
      links: auhtorLinks.length ? auhtorLinks : [],
      span: book.date? `Publicado por primera vez en ${book.date}` : undefined,
    }
  }

  itemToBook(item: Card): BookDetail{
    let authorList = item.links?.map(author =>{
      return {
        olid: author.route.split('/author/')[1],
        name: author.name
      }});

    return {
      olid: item.olid,
      imageUrl: item.imageUrl ?? '',
      title: item.title ?? '',
      authorList: authorList ?? [],
      date: item.span ? item.span.split('Publicado por primera vez en ')[1] : '',
      fav: item.buttons? item.buttons[0].clicked : undefined,
      type: item.url.split('/')[1] + 's',
      subtitle: '',
      description: ''
      };
  }

  authorToItem(author: AuthorDetail): Card{
    let dates: string = '';
    if(author.birth_date){
      if(author.death_date) dates = `${author.birth_date} - ${author.death_date}`;
      else dates = `${author.birth_date}`;
    }

    return{
      olid: author.olid,
      url: `/author/${author.olid}`,
      imageUrl: author.photo ?? '',
      title: author.name,
      span: dates
    }
  }
}


