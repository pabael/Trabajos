import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';

import { TestBed } from "@angular/core/testing";
import { BooksService } from "./library-books.service";
import { BookDetail } from "../components/library-model/library-book-detail";
import { of } from "rxjs";
import { ApiBookResponse } from "../components/library-model/library-api-book-response";
import { ApiAuthorResponse } from "../components/library-model/library-api-author-response";
import { ApiWorksResponse, Entry } from "../components/library-model/library-api-works-response";
import { AuthorDetail } from "../components/library-model/library-auhtor-detail";
import { Card } from "../components/library-model/library-card";

describe('Service', () => {

  const itemMock1: Card ={
    olid: '1',
    url: '1',
    title: '1',
    imageUrl: '1',
    buttons: [{name:'fav', clicked: true}, {name:'add', clicked: true}]
  };

  const itemMock2: Card ={
    olid: '2',
    url: '2',
    title: '2',
    imageUrl: '2',
    buttons: [{name:'fav', clicked: true}, {name:'add', clicked: true}]
  };

  const itemMock3: Card ={
    olid: '3',
    url: '3',
    title: '3',
    imageUrl: '3',
    buttons: [{name:'fav', clicked: false}, {name:'add', clicked: true}]
  };

  let listCardMockFav: Card[];

  let listCardMockTodos:Card[];

  const itemMock4: Card = {
    olid: '4',
    url: '4',
    title: '4',
    imageUrl: '4',
    buttons: [{name:'fav', clicked: false}, {name:'add', clicked: true}]
  }


  const listMockFav: BookDetail[] =[
    {
      olid: '1',
      imageUrl:'1',
      title: '1',
      authorList: [{olid: '1', name: 'author1'}],
      date: '1998',
      fav: true,
      type: 'books',
      subtitle: '1',
      description: '1'
    }
  ];
  const listMockTodos:BookDetail[] = [
    {
      olid: '2',
      imageUrl:'2',
      title: '2',
      authorList: [{olid: '2', name: 'author2'}],
      date: '2998',
      fav: false,
      type: 'books',
      subtitle: '2',
      description: '2'
    },
    {
      olid: '3',
      imageUrl:'3',
      title: '3',
      authorList: [{olid: '3', name: 'author3'}],
      date: '3998',
      fav: false,
      type: 'books',
      subtitle: '3',
      description: '3'
    }
  ];

  let service: BooksService;
  let apiAuthorResponse: Partial<ApiAuthorResponse>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule, HttpClientTestingModule],
      providers: [BooksService]
    }).compileComponents();

    service = TestBed.inject(BooksService);
    listMockFav[0].fav = true;
    listMockTodos[0].fav = false;
    listMockTodos[1].fav = false;
    service.allBooks = listMockFav.concat(listMockTodos);
    listCardMockTodos = [itemMock2, itemMock3 ];
    listCardMockFav = [itemMock1];

    apiAuthorResponse = {
      "name": "Gabriel García Márquez",
      "birth_date": "1927",
      "alternate_names": [
        "Gabriel José de la Concordia García Márquez",
        "Gabriel Garcia Marquez"
      ],
      "personal_name": "Gabriel Garcia Marquez",
      "type": {
        "key": "/type/author"
      },
      "source_records": [
        "amazon:9588886171"
      ],
      "key": "/authors/OL4586796A",
      "bio": "Gabriel García Márquez is a Colombian novelist",
      "photos": [
        6275915,
        3365866,
        6900509,
        3365865
      ],
      "death_date": "2014"
    }
  });

  it('errors', () => {
    const errorMock = new HttpErrorResponse({
      error: 'Error de servidor',
      status: 500,
      statusText: 'Error interno del servidor'
    })
    service.setError = errorMock;
    expect(service.getError).toEqual(errorMock);
  });

  describe('Services for main-page', () =>{

    it('Remove from Todos, add to fav', () => {
      service.updateLists(listCardMockTodos[0]);
      expect(service.getFavList.length).toBe(2);
      expect(service.getNoFavList.length).toBe(1);
    });

    it('Remove from fav, add to Todos', () => {
      service.updateLists(listCardMockFav[0]);
      expect(service.getFavList.length).toBe(0);
      expect(service.getNoFavList.length).toBe(3);
    });

    it('Add book to fav if book is not in any list', () =>{
      service.updateLists(itemMock4);
      expect(service.getFavList.length).toBe(2);
      expect(service.getNoFavList.length).toBe(2);
    });

    it('Add book to todos if book is not in any list', () =>{
      service.addBook(itemMock4);
      expect(service.getFavItems.length).toBe(1);
      expect(service.getNoFavItems.length).toBe(3);
    });

    it('Add book to todos if book in list', () => {
      listMockFav[0].fav = true;
      listMockTodos[0].fav = false;
      listMockTodos[1].fav = false;
      service.allBooks = listMockFav.concat(listMockTodos);

      service.addBook(listCardMockTodos[0])
      expect(service.getNoFavList.length).toBe(1);
      expect(service.getFavList.length).toBe(1);
      expect(service.allBooks.length).toBe(2);
    });
  });

  describe('Services for bookDetailComponent', () =>{

    it('Change fav property', () => {
      listMockFav[0].fav = true;
      service.allBooks = listMockFav.concat(listMockTodos);
      service.updateLists(listCardMockFav[0]);
      expect(listMockFav[0].fav).toBeFalse();
    });

    describe('APi calls and managment of its data response', () =>{
      let bookDetailMock:BookDetail;
      let workDetailMock: BookDetail;
      let httpMock: HttpTestingController;
      let apiResponseBookMock: Partial<ApiBookResponse>;
      let apiResponseAuthorWorksMock: Partial<ApiWorksResponse>;
      let entry: Entry;


      beforeEach(() => {
        apiResponseBookMock = {
          "subtitle": "Timeless lessons on wealth, greed, and happiness",
          "full_title": "The Psychology of Money Timeless lessons on wealth, greed, and happiness",
          "key": "/books/OL29412746M",
          "authors": [{"author": {"key": "/authors/OL4586796A"}}],
          "title": "The Psychology of Money",
          "number_of_pages": 256,
          "publish_date": "Sep 08, 2020",
          "works": [{"key": "/works/OL21640039W"}],
          "type": {
            "key": "/type/work"
          },
          "description": {
            "type": "/type/text",
            "value": "description"
          },
          "covers": [
            13454191
          ]
        };

        entry = {
          "title": "The Psychology of Money",
            "authors": [
              {
                "type": {
                  "key": "/type/author_role"
                },
                "author": {
                  "key": "/authors/OL10826079A"
                }
              }
            ],
            "covers": [
              13454191
            ],
            "key": "/works/OL21640039W"
        };

        apiResponseAuthorWorksMock ={
          "links": {
            "self": "/authors/OL4586796A/works.json?",
            "author": "/authors/OL4586796A"
          },
          "size": 414,
          "entries": [entry]
        };

        bookDetailMock = {
          olid: 'OL21640039W',
          imageUrl:'https://covers.openlibrary.org/b/olid/OL21640039W.jpg',
          title: 'The Psychology of Money',
          authorList: [{olid: 'OL4586796A', name: 'Gabriel García Márquez'}],
          date: '2020',
          fav: undefined,
          type: 'books',
          subtitle: 'Timeless lessons on wealth, greed, and happiness',
          description: 'description'
        };

        workDetailMock = {
          olid: 'OL21640039W',
          imageUrl:'https://covers.openlibrary.org/b/id/13454191.jpg',
          title: 'The Psychology of Money',
          authorList: [{olid: 'OL4586796A', name: 'Gabriel García Márquez'}],
          date: "2020",
          fav: undefined,
          type: 'works',
          subtitle: 'Timeless lessons on wealth, greed, and happiness',
          description: 'description'
        };

        httpMock = TestBed.inject(HttpTestingController);
        service.allBooks = listMockFav.concat(listMockTodos);
      });

      it('author list empty', (done) => {
        apiResponseBookMock.authors = [];
        apiResponseBookMock.type!.key = "/type/edition";

        service.getBookFromOlid(bookDetailMock.olid, '').subscribe((response) => {
          expect(response.authorList.length).toBe(0);
          done();
        });

        const reqBook = httpMock.expectOne(`https://openlibrary.org/books/${bookDetailMock.olid}.json`);
        expect(reqBook.request.method).toBe('GET');
        reqBook.flush(apiResponseBookMock);
        httpMock.verify();
      });

      it('getAuthorFromOlid', (done) => {
        service.getAuthorFromOlid(workDetailMock.authorList[0].olid).subscribe(response => {
          expect(response).toEqual(apiAuthorResponse);
          done();
        });
        const req = httpMock.expectOne(`https://openlibrary.org/authors/${workDetailMock.authorList[0].olid}.json`);
        expect(req.request.method).toBe('GET');
        req.flush(apiAuthorResponse);
        httpMock.verify();
      });

      describe('getAuthorBooks', () => {
        it('all info', (done) => {
          service.allBooks.push(workDetailMock);
          service.getAuthorBooks(workDetailMock.authorList[0].olid).subscribe(response =>{
            expect(response).toEqual([workDetailMock]);
            done();
          })

          const reqWork = httpMock.expectOne(`https://openlibrary.org/authors/${workDetailMock.authorList[0].olid}/works.json`);
          expect(reqWork.request.method).toBe('GET');
          reqWork.flush(apiResponseAuthorWorksMock);

          const reqBook = httpMock.expectOne(`https://openlibrary.org/works/${workDetailMock.olid}.json`);
          expect(reqBook.request.method).toBe('GET');
          reqBook.flush(apiResponseBookMock);

          const reqAuthor = httpMock.expectOne(`https://openlibrary.org/authors/${workDetailMock.authorList[0].olid}.json`);
          expect(reqAuthor.request.method).toBe('GET');
          reqAuthor.flush(apiAuthorResponse);

          httpMock.verify();
        });

        it('set books type', (done) => {
          apiResponseBookMock.authors = [{"key": "/authors/OL4586796A"}];
          apiResponseBookMock.type!.key = "/type/edition";
          service.getBookFromOlid(bookDetailMock.olid, '').subscribe(response => {
            expect(response).toEqual(bookDetailMock);
            done();
          })

          const reqBook = httpMock.expectOne(`https://openlibrary.org/books/${bookDetailMock.olid}.json`);
          expect(reqBook.request.method).toBe('GET');
          reqBook.flush(apiResponseBookMock);

          const reqAuthor = httpMock.expectOne(`https://openlibrary.org/authors/${workDetailMock.authorList[0].olid}.json`);
          expect(reqAuthor.request.method).toBe('GET');
          reqAuthor.flush(apiAuthorResponse);
        });

        it('no works', (done) => {
          apiResponseAuthorWorksMock.entries = undefined;
          service.getAuthorBooks(bookDetailMock.authorList[0].olid).subscribe(response =>{
            expect(response).toEqual([]);
            done();
          })

          const reqWork = httpMock.expectOne(`https://openlibrary.org/authors/${bookDetailMock.authorList[0].olid}/works.json`);
          expect(reqWork.request.method).toBe('GET');
          reqWork.flush(apiResponseAuthorWorksMock);
          httpMock.verify();
        });

        it('more than 6 works', (done) => {
          spyOn(service, 'getBookFromOlid').and.returnValue(of (bookDetailMock));
          apiResponseAuthorWorksMock.entries = [entry, entry, entry, entry, entry, entry, entry];
          service.getAuthorBooks(bookDetailMock.authorList[0].olid).subscribe(response =>{
            expect(response).toEqual([bookDetailMock, bookDetailMock, bookDetailMock, bookDetailMock, bookDetailMock, bookDetailMock]);
            done();
          })

          const reqWork = httpMock.expectOne(`https://openlibrary.org/authors/${bookDetailMock.authorList[0].olid}/works.json`);
          expect(reqWork.request.method).toBe('GET');
          reqWork.flush(apiResponseAuthorWorksMock);
          httpMock.verify();
        });
      })
    });
  });

  describe('Service for search page', () =>{
    let httpMock: HttpTestingController;
    const bookDetailMock: BookDetail = {
      olid: '1',
      imageUrl:'https://covers.openlibrary.org/b/olid/1.jpg',
      title: 'The Psychology of Money',
      authorList: [],
      date: "Sep 08, 2020",
      fav: false,
      type: '',
      subtitle: 'Timeless lessons on wealth, greed, and happiness',
      description: 'description'
    };
    const listBookDetailMock: BookDetail[] = [
      bookDetailMock,
      bookDetailMock,
      bookDetailMock,
      bookDetailMock,
      bookDetailMock,
      bookDetailMock
    ];
    const titleMock: string = "happy";
    const urlOptionMock: string = "title";
    const olidListMock: string[] = ['3', '4','5','6','7','8'];
    const apiBookResponseMock ={
        "start": 0,
        "num_found": 629,
        "docs": [
          {
            "edition_count": 1,
            "edition_key": "3",
          },
          {
            "edition_count": 1,
            "edition_key": "4",
          },
          {
            "edition_count": 1,
            "edition_key": "5",
          },
          {
            "edition_count": 1,
            "edition_key": "6",
          },
          {
            "edition_count": 1,
            "edition_key": "7",
          },
          {
            "edition_count": 1,
            "edition_key": "8",
          },
          {
            "edition_count": 1,
            "edition_key": "9",
          },
        ]
    };

    beforeEach(() => {
      httpMock = TestBed.inject(HttpTestingController);
    });

    it('searchBooksApi', (done) => {
      service.searchBooksApi(urlOptionMock, titleMock).subscribe(response => {
        expect(response).toEqual(olidListMock);
        done();
      });

      const req = httpMock.expectOne(`https://openlibrary.org/search.json?title=${titleMock}`);
      expect(req.request.method).toBe('GET');
      req.flush(apiBookResponseMock);
      httpMock.verify();
    });

    it('searchBooks', (done) =>{
      spyOn(service, 'searchBooksApi').and.returnValue( of (olidListMock));
      let spy = spyOn(service, 'getBookFromOlid').and.returnValue( of (bookDetailMock));
      service.searchBooks('title', '1').subscribe(response => {
        expect(response).toEqual(listBookDetailMock);
        done();
      });
      expect(spy).toHaveBeenCalledTimes(6);
    });

    const authorDetail: AuthorDetail = {
      olid:            'OL4586796A',
      name:            "Gabriel García Márquez",
      alternate_names:  ["Gabriel José de la Concordia García Márquez", "Gabriel Garcia Marquez"],
      birth_date:      "1927",
      death_date:      "2014",
      bio:             "Gabriel García Márquez is a Colombian novelist",
      photo:           `https://covers.openlibrary.org/a/id/6275915-M.jpg`,
      books:           [],
    };

    const apiAuthorResponse2: Partial<ApiAuthorResponse> = {
      "name": "Gabriel García Márquez",
      "birth_date": "1927",
      "alternate_names": [
        "Gabriel José de la Concordia García Márquez",
        "Gabriel Garcia Marquez"
      ],
      "personal_name": "Gabriel Garcia Marquez",
      "type": {
        "key": "/type/author"
      },
      "source_records": [
        "amazon:9588886171"
      ],
      "key": "/authors/OL4586796A",
      "bio": "Gabriel García Márquez is a Colombian novelist",
      "photos": [
        6275915,
        3365866,
        6900509,
        3365865
      ],
      "death_date": "2014"
    };

    [
      {
        testId: 'author response with info',
        authorDetail: authorDetail,
        apiResponse: apiAuthorResponse2
      },
      {
        testId: 'author response without info',
        authorDetail: {...authorDetail, photo: ''},
        apiResponse: {...apiAuthorResponse2, photos: undefined}
      }
    ].forEach((data: any) =>
      it(`${data.testId}`, (done) =>{
        spyOn(service, 'getAuthorFromOlid').and.returnValue(of (data.apiResponse));
        service.setAuthorInfo(data.authorDetail.olid).subscribe(response => {
          expect(response).toEqual(data.authorDetail);
          done();
        });
      })
    );
  })
})
