import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from '@angular/common/http';
import { Component, Output, EventEmitter, Input} from '@angular/core';

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BooksService } from "../../services/library-books.service";
import { AuthorDetail } from "../../components/library-model/library-auhtor-detail";
import { AuthorDetailPageComponent } from "./library-author-detail-page.component";

@Component({
  selector: 'app-author-detail',
  template: `<button class = "button1" (click)="button1()">Click</button>
             <button class = "button2" (click)="button2()">Click</button>`,
  styles: ''
})
export class AuthorDetailMock {
  @Input() public author: AuthorDetail = {
    olid:            '',
    name:            '',
    alternate_names: [],
    birth_date:      '',
    bio:             '',
    photo:           '',
    books:           [],
  };

  constructor(private service: BooksService) {
  }

  loading: boolean[] = [false, false];

  @Output()
  public onButton1: EventEmitter<void> = new EventEmitter<void>();

  @Output()
  public onButton2: EventEmitter<void> = new EventEmitter<void>();

  public button1():void{
    this.onButton1.emit();
  }

  public button2():void{
    this.onButton2.emit();
  }
}

describe('Book detail page', () => {
  let component: AuthorDetailPageComponent;
  let fixture: ComponentFixture<AuthorDetailPageComponent>;
  let element: HTMLElement;
  let service: BooksService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [AuthorDetailPageComponent, AuthorDetailMock]
    }).compileComponents();
    service = TestBed.inject(BooksService);
    fixture = TestBed.createComponent(AuthorDetailPageComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;
    fixture.detectChanges();
  });

  it('Call service when fav botton change in any book item', () => {
    const result = spyOn(service, 'updateLists');
    element.querySelector<HTMLElement>('app-author-detail .button1')!.click();
    expect(result).toHaveBeenCalled();
  });

  it('Call service when add botton change in any book item', () => {
    const result = spyOn(service, 'addBook');
    element.querySelector<HTMLElement>('app-author-detail .button2')!.click();
    expect(result).toHaveBeenCalled();
  });

})
