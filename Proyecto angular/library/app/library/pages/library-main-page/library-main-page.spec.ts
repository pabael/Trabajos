import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from '@angular/common/http';
import { Component, Output, EventEmitter, Input} from '@angular/core';

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { MainPageComponent } from "./library-main-page.component";
import { BooksService } from "../../services/library-books.service";
import { Card } from "../../components/library-model/library-card";

@Component({
  selector: 'app-book-list',
  template: `<button class="b1" (click)="b1()">Click</button>
             <button class="b2" (click)="b2()">Click</button> `,
  styles: ''
})
export class BookListMock {
  @Input() public list: string[] = [];

  @Output()
  public onButton1: EventEmitter<any> = new EventEmitter();
  @Output()
  public onButton2: EventEmitter<any> = new EventEmitter();

  public b1():void{
    this.onButton1.emit();
  }

  public b2():void{
    this.onButton2.emit();
  }
}

const itemMock: Card ={
  olid: '1',
  url: '1',
  title: '1',
  imageUrl: '1'
};

describe('MainPage', () => {

  let component: MainPageComponent;
  let fixture: ComponentFixture<MainPageComponent>;
  let element: HTMLElement;
  let listService: BooksService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [MainPageComponent, BookListMock]
    }).compileComponents();
    listService = TestBed.inject(BooksService);
    fixture = TestBed.createComponent(MainPageComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;

    spyOnProperty(listService, 'getFavItems').and.returnValue([itemMock, itemMock]);
    spyOnProperty(listService, 'getNoFavItems').and.returnValue([itemMock, itemMock]);
    fixture.detectChanges();
  });

  it('Show both lists', () => {
    const lists = element.querySelectorAll('app-book-list');
    expect(lists?.length).toBe(2);
  });

  it('Call service when fav botton change in any book item', () => {
    const result = spyOn(listService, 'updateLists');
    element.querySelector<HTMLElement>('.fav-container .b1')!.click();
    expect(result).toHaveBeenCalled();
  });

  it('Call service when add botton change in any book item', () => {
    const result = spyOn(listService, 'addBook');
    element.querySelector<HTMLElement>('.fav-container .b2')!.click();
    expect(result).toHaveBeenCalled();
  });

})
