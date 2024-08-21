import { Component } from '@angular/core';
import { BooksService } from '../../services/library-books.service';
import { Card } from '../../components/library-model/library-card';

@Component({
  selector: 'app-main',
  templateUrl: './library-main-page.component.html',
  styleUrls: ['./library-main-page.component.sass']
})
export class MainPageComponent {

  constructor(private listService: BooksService){
  }

  button1(bookChanged:Card): void{
    this.listService.updateLists(bookChanged);
  }

  button2(book:Card): void{
    this.listService.addBook(book);
  }

  get getFavList():Card[]{
    return this.listService.getFavItems;
  }

  get getNoFavList():Card[]{
    return this.listService.getNoFavItems;
  }

}
