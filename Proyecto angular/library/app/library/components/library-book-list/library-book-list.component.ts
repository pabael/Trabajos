import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Card } from '../library-model/library-card';

@Component({
  selector: 'app-book-list',
  templateUrl: './library-book-list.component.html',
  styleUrls: ['./library-book-list.component.sass']
})
export class BookListComponent {
  @Input() public list: Card[] = [];

  @Output()
  public onButton1: EventEmitter<Card> = new EventEmitter<Card>();

  @Output()
  public onButton2: EventEmitter<Card> = new EventEmitter<Card>();

  constructor(){ }

  public button1(item:Card):void{
    this.onButton1.emit(item);
  }

  public button2(item:Card):void{
    this.onButton2.emit(item);
  }
}
