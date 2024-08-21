import { Component, OnInit } from '@angular/core';
import { BooksService } from '../../services/library-books.service';
import { Card } from '../../components/library-model/library-card';

@Component({
  selector: 'app-search-page',
  templateUrl: './library-search-page.component.html',
  styleUrls: ['./library-search-page.component.sass']
})
export class SearchPageComponent implements OnInit{

  resultList: Card[] = [];
  loading: boolean = false;

  constructor(private service: BooksService){
  }

  ngOnInit(): void {
    if(sessionStorage.getItem('searchList') !== null) this.resultList = JSON.parse(sessionStorage.getItem('searchList')!);
  }

  searching(){
    this.loading = true;
  }

  setResultList(list: Card[]){
    this.resultList = list;
    this.loading = false;
  }

  button1(book:Card): void{
    this.service.updateLists(book);
  }

  button2(book:Card): void{
    this.service.addBook(book);
  }
}
