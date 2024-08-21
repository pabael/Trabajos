import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BookDetailComponent } from "./library-book-detail.component";
import { BookDetail } from "../library-model/library-book-detail";
import { BooksService } from "../../services/library-books.service";
import { of, throwError } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { DefaultImagePipe } from "../../pipes/default-image.pipe";

describe('BookDetail', () => {

  let component: BookDetailComponent;
  let fixture: ComponentFixture<BookDetailComponent>;
  let element: HTMLElement;
  let service: BooksService;
  let bookdetailMock: BookDetail;
  let router: Router;

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
      declarations: [BookDetailComponent,DefaultImagePipe]
    }).compileComponents();

    bookdetailMock = {
      olid: '1',
      imageUrl:'https://covers.openlibrary.org/b/olid/1.jpg',
      title: 'title1',
      authorList: [{olid: '1', name: 'author1'}],
      date: '1998',
      fav: false,
      type: 'books',
      subtitle: 'subtitle1',
      description: 'description1'
    };

    router = TestBed.inject(Router);
    service = TestBed.inject(BooksService);
    fixture = TestBed.createComponent(BookDetailComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;
  });

  describe('ngOnInit', () => {
    it('url work', () => {
      spyOnProperty(router, 'url').and.returnValue('/work/1');
      fixture.detectChanges();

      expect(component.bookDetail.type).toEqual('works');
    });

    it('url book', () => {
      spyOnProperty(router, 'url').and.returnValue('/book/1');
      fixture.detectChanges();

      expect(component.bookDetail.type).toEqual('books');
    });
  })

  describe('All ok', () => {

    beforeEach( () => {
      spyOn(service, 'getBookFromOlid').and.returnValue(
        of(bookdetailMock)
      );

      fixture.detectChanges();
    });

    it('Show book detail information', () => {
      expect(element.querySelector('h1')?.textContent).toContain(bookdetailMock.title);
      expect(element.querySelector('h2')?.textContent).toContain(bookdetailMock.subtitle);
      element.querySelectorAll('a').forEach((link, index) => {
        expect(link.textContent).toContain(bookdetailMock.authorList[index].name);
      });
      expect(element.querySelector('p')?.textContent).toContain(bookdetailMock.description);
      expect(element.querySelector('figcaption')?.textContent).toContain(bookdetailMock.date);

    });

    it('Emit event with book item when fav button clicked', () => {
      component.bookDetail = bookdetailMock;
      fixture.detectChanges();

      const result = spyOn(component.onClickFav, 'emit');
      component.clickFav(new MouseEvent('click'));
      expect(result).toHaveBeenCalledOnceWith(bookdetailMock);
    });
  });

    it('Emit event with book item when add button clicked', () => {
      component.bookDetail = bookdetailMock;
      fixture.detectChanges();

      const result = spyOn(component.onClickAdd, 'emit');
      component.clickAdd(new MouseEvent('click'));
      expect(result).toHaveBeenCalledOnceWith(bookdetailMock);
    });


  describe('Fail getDetailBook', () => {
    let spySetError: any;

    beforeEach(() => {
      spyOn(service, 'getBookFromOlid').and.returnValue(
        throwError(() => HttpErrorResponse)
      );
      spySetError = spyOnProperty(service, 'setError', 'set');
      fixture.detectChanges();
    })

    it('set error', () => {
      expect(spySetError).toHaveBeenCalled();
    });
  });

  describe('Render correct styles', () => {
    beforeEach(() => {
      component.bookDetail = bookdetailMock;
      fixture.detectChanges();
    });

    it('Should render fav when fav is true', () => {
      component.bookDetail.fav = true;
      component.loading = false;
      fixture.detectChanges();

      const buttonElement: HTMLElement | null = element.querySelector('button');
      expect(buttonElement?.classList).toContain('book-fav');
    });

    it('should render no-fav when fav is false', () => {
      component.bookDetail.fav = false;
      component.loading = false;
      fixture.detectChanges();

      const buttonElement: HTMLElement | null = element.querySelector('button');
      expect(buttonElement?.classList).toContain('book-no-fav');
    });

  });
})
