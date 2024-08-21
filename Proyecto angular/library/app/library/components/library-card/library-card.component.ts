import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Card } from '../library-model/library-card';

@Component({
  selector: 'app-card',
  templateUrl: './library-card.component.html',
  styleUrls: ['./library-card.component.sass']
})
export class CardComponent {
  @Input() public item: Card ={
    olid: '',
    url: '',
    title: '',
    imageUrl: ''
  };

  @Output()
  public onButton1: EventEmitter<Card> = new EventEmitter<Card>();

  @Output()
  public onButton2: EventEmitter<Card> = new EventEmitter<Card>();

  constructor(){ }

  public button1(event: MouseEvent):void{
    event?.stopPropagation();
    this.onButton1.emit(this.item);
  }

  public button2(event: MouseEvent):void{
    event?.stopPropagation();
    this.onButton2.emit(this.item);
  }
}
