import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from '@angular/common/http';
import { Component, Output, EventEmitter, Input} from '@angular/core';
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BooksService } from "../../services/library-books.service";
import { SearchPageComponent } from "./library-search-page.component";
import { ReactiveFormsModule } from "@angular/forms";

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

@Component({
  selector: 'app-search',
  template: `<button class="search" (click)="searching()"></button>
             <button class="results" (click)="sendResultList()"></button>`,
  styles: ''
})
export class SearchMock {

  @Output()
  public onSearching: EventEmitter<void> = new EventEmitter<void>();

  @Output()
  public onResultList: EventEmitter<object[]> = new EventEmitter<object[]>();

  searching(): void{
    this.onSearching.emit();
  }

  sendResultList():void{
    this.onResultList.emit([{
      olid: '1',
      imageUrl:'1',
      title: '1',
      authorList: [],
      date: new Date(1998),
      fav: false,
      type: ''
    }]);
  }
}

describe('SearchPage', () => {

  let component: SearchPageComponent;
  let fixture: ComponentFixture<SearchPageComponent>;
  let element: HTMLElement;
  let service: BooksService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule,ReactiveFormsModule],
      declarations: [SearchPageComponent, BookListMock, SearchMock]
    }).compileComponents();
    service = TestBed.inject(BooksService);
    fixture = TestBed.createComponent(SearchPageComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;
    fixture.detectChanges();
  });

  it('Call service when fav botton change in any book item', () => {
    const result = spyOn(service, 'updateLists');
    element.querySelector<HTMLElement>('app-book-list .b1')!.click();
    expect(result).toHaveBeenCalled();
  });

  it('Call service when add botton change in any book item', () => {
    const result = spyOn(service, 'addBook');
    element.querySelector<HTMLElement>('app-book-list .b2')!.click();
    expect(result).toHaveBeenCalled();
  });

  it('Loading true when recive searching', () =>{
    element.querySelector<HTMLElement>('app-search .search')!.click();
    expect(component.loading).toBeTrue();
  })

  // it('Loading false when recive result list and list save', () =>{
  //   element.querySelector<HTMLElement>('app-search .results')!.click();
  //   expect(component.loading).toBeFalse();
  //   expect(component.resultList).toEqual([{
  //     olid: '1',
  //     imageUrl:'1',
  //     title: '1',
  //     authorList: [],
  //     date: new Date(1998),
  //     fav: false,
  //     type: ''
  //   }]);
  // })
})


