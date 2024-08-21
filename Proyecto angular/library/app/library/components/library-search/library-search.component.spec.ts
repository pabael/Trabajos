import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BooksService } from "../../services/library-books.service";
import { ReactiveFormsModule } from "@angular/forms";
import { BookDetail } from "../library-model/library-book-detail";
import { SearchComponent } from "./library-search.component";
import { of, throwError } from "rxjs";
import { Card } from "../library-model/library-card";
import { AuthorDetail } from "../library-model/library-auhtor-detail";

describe('SearchComponent', () => {

  let component: SearchComponent;
  let fixture: ComponentFixture<SearchComponent>;
  let element: HTMLElement;
  let service: BooksService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule,ReactiveFormsModule],
      declarations: [SearchComponent]
    }).compileComponents();
    service = TestBed.inject(BooksService);
    fixture = TestBed.createComponent(SearchComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;
    fixture.detectChanges();
  });

  describe('onSubmit', () =>{
    const itemMock1: Card ={
      olid: '1',
      url: '1',
      title: '1',
      imageUrl: '1'
    };

    const bookMock: BookDetail =
    {
      olid: '1',
      imageUrl:'https://covers.openlibrary.org/b/olid/1.jpg',
      title: 'title1',
      authorList: [],
      date: '',
      fav: false,
      type: 'books',
      subtitle: 'subtitle1',
      description: 'description1',
    }
    const listBookMocks:BookDetail[] = [bookMock];
    const listAuthorMock: AuthorDetail[] = [{
      olid:            '1',
      name:            "name1",
      alternate_names:  ["1"],
      birth_date:      "1927",
      death_date:      "2014",
      bio:             "1111111",
      photo:           `https://covers.openlibrary.org/a/id/1-M.jpg`,
      books:           [bookMock, bookMock]
    }];

    describe('All ok', () =>{
      beforeEach(() =>{
        spyOn(service, 'searchBooks').and.returnValue(of(listBookMocks));
        spyOn(service, 'bookToItem').and.returnValue(itemMock1);

        spyOn(service, 'searchAuhtors').and.returnValue(of(listAuthorMock));
        spyOn(service, 'authorToItem').and.returnValue(itemMock1);

        fixture.detectChanges();
      })

      it('searchBook', () => {
        component.searchType = 'book';

        component.onSubmit();
        expect(component.resultList.length).toBe(1);
      });

      it('searchAuthor', () => {
        component.searchType = 'author';

        component.onSubmit();
        expect(component.resultList.length).toBe(1);
      });
    })

    describe('Search fail', () =>{

      it('Fail searchBooks', () => {
        spyOn(service, 'searchBooks').and.returnValue(
          throwError(() => HttpErrorResponse)
        );
        const spySetError = spyOnProperty(service, 'setError', 'set');
        component.searchType = 'book';

        fixture.detectChanges();

        component.onSubmit();
        expect(spySetError).toHaveBeenCalled();
      });

      it('Fail searchAuthor', () => {
        spyOn(service, 'searchAuhtors').and.returnValue(
          throwError(() => HttpErrorResponse)
        );
        const spySetError = spyOnProperty(service, 'setError', 'set');
        component.searchType = 'author';

        fixture.detectChanges();

        component.onSubmit();
        expect(spySetError).toHaveBeenCalled();
      })
    })
  })
})

