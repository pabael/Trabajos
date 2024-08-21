import { Component, OnInit, Input, Output, EventEmitter  } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BooksService } from '../../services/library-books.service';
import { BookDetail } from '../library-model/library-book-detail';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-book-detail',
  templateUrl: './library-book-detail.component.html',
  styleUrls: ['./library-book-detail.component.sass']
})
export class BookDetailComponent implements OnInit{

  @Input() public bookDetail: BookDetail = {
    olid: '',
    imageUrl: '',
    title: '',
    authorList: [],
    date: '',
    fav: undefined,
    type: '',
    subtitle: '',
    description: ''
    };

  loading: boolean = false;

  @Output()
  public onClickFav: EventEmitter<BookDetail> = new EventEmitter<BookDetail>();

  @Output()
  public onClickAdd: EventEmitter<BookDetail> = new EventEmitter<BookDetail>();

  constructor(private route: ActivatedRoute, private booksService: BooksService, private router: Router) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.bookDetail.olid = params['olid'];
      if(this.router.url === `/work/${this.bookDetail.olid}`) this.bookDetail.type = 'works';
      if(this.router.url === `/book/${this.bookDetail.olid}`) this.bookDetail.type = 'books';
      this.setDetailBook();
    });
  }

  clickFav(event: MouseEvent): void{
    event?.stopPropagation();
    this.onClickFav.emit(this.bookDetail);
  }

  clickAdd(event: MouseEvent): void{
    event?.stopPropagation();
    this.onClickAdd.emit(this.bookDetail);
  }

  private setDetailBook(){
    this.loading = true;
    this.booksService.getBookFromOlid(this.bookDetail.olid, this.bookDetail.type)
    .subscribe({
      next:bookResponse => {
        this.bookDetail = bookResponse;
        this.loading = false;
      },
      error: (error:HttpErrorResponse) => {
        this.booksService.setError = error;
      }
    });
  }

}
