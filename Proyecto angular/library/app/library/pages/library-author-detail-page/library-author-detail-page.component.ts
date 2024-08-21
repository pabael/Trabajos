import { Component } from '@angular/core';
import { BooksService } from '../../services/library-books.service';
import { Card } from '../../components/library-model/library-card';

@Component({
  selector: 'app-author-detail-page',
  templateUrl: './library-author-detail-page.component.html',
  styleUrls: ['./library-author-detail-page.component.sass']
})
export class AuthorDetailPageComponent{

  constructor(private service: BooksService) {
  }

  button1(book: Card): void{
    this.service.updateLists(book);
  }

  button2(book: Card): void{
    this.service.addBook(book);
  }
}

