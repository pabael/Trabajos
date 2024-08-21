import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { ErrorPageComponent } from "./library-error-page.component";
import { BooksService } from "../../services/library-books.service";

describe('Error page', () => {

  const errorMock:HttpErrorResponse = new HttpErrorResponse({
    error: 'Not Found',
    status: 404,
    statusText: 'Resource not found',
  });

  let component: ErrorPageComponent;
  let fixture: ComponentFixture<ErrorPageComponent>;
  let element: HTMLElement;
  let service: BooksService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [ErrorPageComponent]
    }).compileComponents();
    service = TestBed.inject(BooksService);
    fixture = TestBed.createComponent(ErrorPageComponent);
    component = fixture.componentInstance;
    element = fixture.nativeElement;
  });

  it('Error status 404', () => {
    service.setError = errorMock;
    fixture.detectChanges();
    expect(component.error).toEqual(errorMock);
  });

})
