import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from '@angular/common/http';
import { Component, Output, EventEmitter, Input} from '@angular/core';

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BooksService } from "../../services/library-books.service";
import { BookDetailPageComponent } from "./library-book-detail-page.component";
import { BookDetail } from "../../components/library-model/library-book-detail";

@Component({
  selector: 'app-book-detail',
  template: `<button class = "fav" (click)="clickFav($event)">Click</button>
             <button class = "add" (click)="clickAdd($event)">Click</button>`,
  styles: ''
})
export class BookDetailMock {
  @Input() public bookDetail: BookDetail = {
    olid: '',
    imageUrl: '',
    title: '',
    authorList: [],
    date: '',
    fav: false,
    type: '',
    subtitle: '',
    description: ''
  };
  loading: boolean = false;

  @Output()
  public onClickFav: EventEmitter<BookDetail> = new EventEmitter<BookDetail>();

  @Output()
  public onClickAdd: EventEmitter<BookDetail> = new EventEmitter<BookDetail>();


  constructor(private service: BooksService){
  }

  clickFav(event: MouseEvent): void{
    event?.stopPropagation();
    this.onClickFav.emit(this.bookDetail);
  }

  clickAdd(event: MouseEvent): void{
    event?.stopPropagation();
    this.onClickAdd.emit(this.bookDetail);
  }
}

const bookDetailMock: BookDetail = {
  olid: '1',
  imageUrl: '1',
  title: '1',
  authorList: [],
  date: '1',
  fav: false,
  type: '1',
  subtitle: '1',
  description: '1'
};

describe('Book detail page', () => {
  let component: BookDetailPageComponent;
  let fixture: ComponentFixture<BookDetailPageComponent>;
  let element: HTMLElement;
  let service: BooksService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [BookDetailPageComponent, BookDetailMock]
    }).compileComponents();
    service = TestBed.inject(BooksService);
    fixture = TestBed.createComponent(BookDetailPageComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;
    fixture.detectChanges();
  });

  it('Call service when fav botton change in any book item', () => {
    const result = spyOn(service, 'updateLists');
    element.querySelector<HTMLElement>('app-book-detail .fav')!.click();
    expect(result).toHaveBeenCalled();
  });

  it('Call service when add botton change in any book item', () => {
    const result = spyOn(service, 'addBook');
    element.querySelector<HTMLElement>('app-book-detail .add')!.click();
    expect(result).toHaveBeenCalled();
  });

})
