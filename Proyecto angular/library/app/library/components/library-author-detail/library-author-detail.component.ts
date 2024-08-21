import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BooksService } from '../../services/library-books.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthorDetail } from '../library-model/library-auhtor-detail';
import { Card } from '../library-model/library-card';
import { BookDetail } from '../library-model/library-book-detail';

@Component({
  selector: 'app-author-detail',
  templateUrl: './library-author-detail.component.html',
  styleUrls: ['./library-author-detail.component.sass']
})
export class AuthorDetailComponent implements OnInit{

  @Input() public author: AuthorDetail = {
    olid:            '',
    name:            '',
    alternate_names: [],
    birth_date:      '',
    bio:             '',
    photo:           '',
    books:           [],
  };

  cardList: Card[] = [];

  loading: boolean[] = [false, false];

  @Output()
  public onButton1: EventEmitter<Card> = new EventEmitter<Card>();

  @Output()
  public onButton2: EventEmitter<Card> = new EventEmitter<Card>();
  constructor(private route: ActivatedRoute, private service: BooksService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.author.olid = params['olid'];
      this.loading = [true, true];
      this.setAuthorInfo();
      this.setAuthorBooks();
    });
  }

  private setAuthorInfo(){
    this.service.setAuthorInfo(this.author.olid)
    .subscribe({
      next: authorInfo =>{
       this.author = authorInfo;
       this.loading[0] = false;
      },
      error: (error:HttpErrorResponse) => {
        this.service.setError = error;
      }
    })
  }

  private setAuthorBooks(){
    this.service.getAuthorBooks(this.author.olid).subscribe({
      next: books =>{
        this.author.books = books;
        this.cardList = books.map(book => this.service.bookToItem(book));
        this.loading[1] = false;
      },
       error: (error:HttpErrorResponse) => {
         this.service.setError = error;
       }
    })
  }

  public button1(item:Card):void{
    this.onButton1.emit(item);
  }

  public button2(item:Card):void{
    this.onButton2.emit(item);
  }
}
