import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BookDetail } from "../library-model/library-book-detail";
import { BooksService } from "../../services/library-books.service";
import { of, throwError } from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { AuthorDetailComponent } from "./library-author-detail.component";
import { AuthorDetail } from "../library-model/library-auhtor-detail";
import { BookListComponent } from "../library-book-list/library-book-list.component";
import { Component, EventEmitter, Input, Output } from "@angular/core";
import { DefaultImagePipe } from "../../pipes/default-image.pipe";
import { CardComponent } from "../library-card/library-card.component";

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

describe('Author detail', () => {

  let component: AuthorDetailComponent;
  let fixture: ComponentFixture<AuthorDetailComponent>;
  let element: HTMLElement;
  let service: BooksService;
  let authorDetailMock: AuthorDetail;

  const bookdetailMock: BookDetail = {
    olid: '1',
    imageUrl:'https://covers.openlibrary.org/b/olid/1.jpg',
    title: 'title1',
    authorList: [{olid: '1', name: 'author1'}],
    date: '',
    fav: false,
    type: 'books',
    subtitle: 'subtitle1',
    description: 'description1'
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        {
          provide: BooksService
        },
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({olid: '1'})
          }
        }
      ],
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [AuthorDetailComponent, BookListMock, DefaultImagePipe, CardComponent]
    }).compileComponents();

    authorDetailMock = {
      olid:            '1',
      name:            "name1",
      alternate_names:  ["1"],
      birth_date:      "1927",
      death_date:      "2014",
      bio:             "1111111",
      photo:           `https://covers.openlibrary.org/a/id/1-M.jpg`,
      books:           [bookdetailMock, bookdetailMock],
    };

    service = TestBed.inject(BooksService);
    fixture = TestBed.createComponent(AuthorDetailComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;

  });

  describe('All ok', () => {

    beforeEach( () => {
      spyOn(service, 'setAuthorInfo').and.returnValue(
        of(authorDetailMock)
      );

      spyOn(service, 'getAuthorBooks').and.returnValue(
        of([bookdetailMock, bookdetailMock])
      );

      fixture.detectChanges();
    });

    it('setAuthorBooks', () => {
      expect(component.author.books).toEqual([bookdetailMock, bookdetailMock]);
    });

    it('setAuthorInfo', () => {
      expect(component.author).toEqual(authorDetailMock);
    })

    it('Emit event with book item when fpav button clicked', () => {
      const result = spyOn(component.onButton1, 'emit');
      element.querySelector<HTMLElement>('app-book-list .b1')!.click();
      expect(result).toHaveBeenCalled();
    });

    it('Emit event with book item when add button clicked', () => {
      const result = spyOn(component.onButton2, 'emit');
      element.querySelector<HTMLElement>('app-book-list .b2')!.click();
      expect(result).toHaveBeenCalled();
    });

  });

  describe('Fail setAuthorInfo', () => {
    let spySetError: any;

    beforeEach(() => {
      spyOn(service, 'setAuthorInfo').and.returnValue(
        throwError(() => HttpErrorResponse)
      );
      spySetError = spyOnProperty(service, 'setError', 'set');
      fixture.detectChanges();
    });

    it('set error', () => {
      component.ngOnInit();
      expect(spySetError).toHaveBeenCalled();
    });
  });

  describe('Fail getAuthor', () => {
    let spySetError: any;
    beforeEach(() => {
      spyOn(service, 'getAuthorBooks').and.returnValue(
        throwError(() => HttpErrorResponse)
      );
      spySetError = spyOnProperty(service, 'setError', 'set');
      fixture.detectChanges();
    })

    it('set error', () => {
      expect(spySetError).toHaveBeenCalled();
    });
  });
})
