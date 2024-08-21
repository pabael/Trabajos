import { Component } from '@angular/core';
import { BooksService } from '../../services/library-books.service';
import { BookDetail } from '../../components/library-model/library-book-detail';

@Component({
  selector: 'app-book-detail-page',
  templateUrl: './library-book-detail-page.component.html',
  styleUrls: ['./library-book-detail-page.component.sass']
})
export class BookDetailPageComponent {
  constructor(private service: BooksService){
  }

  clickFav(book: BookDetail){
    book.fav = !book.fav;
    this.service.updateLists(this.service.bookToItem(book));
  }

  addBook(book:BookDetail): void{
    if(book.fav !== undefined) book.fav = undefined;
    else book.fav = false;
    this.service.addBook(this.service.bookToItem(book));
  }
}
