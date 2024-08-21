import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from '@angular/common/http';

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { BookListComponent } from "./library-book-list.component";
import { Card } from "../library-model/library-card";
import { CardComponent } from "../library-card/library-card.component";
import { DefaultImagePipe } from "../../pipes/default-image.pipe";

describe('BookList', () => {

  const itemMock1: Card ={
    olid: '1',
    url: '1',
    title: '1',
    imageUrl: '1'
  };

  const itemMock2: Card ={
    olid: '1',
    url: '1',
    title: '1',
    imageUrl: '1'
  };

  const itemMock3: Card ={
    olid: '1',
    url: '1',
    title: '1',
    imageUrl: '1'
  };

  const listMock: Card[] = [itemMock1, itemMock2, itemMock3];

  let component: BookListComponent;
  let fixture: ComponentFixture<BookListComponent>;
  let element: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [BookListComponent, CardComponent, DefaultImagePipe]
    }).compileComponents();

    fixture = TestBed.createComponent(BookListComponent);
    component = fixture.componentInstance;
    component.list = [itemMock1, itemMock2, itemMock3];
    element = fixture.nativeElement;
    fixture.detectChanges();
  });

  it('Show list', () =>{
    const bookItems = element.querySelectorAll('app-card');
    expect(bookItems?.length).toBe(listMock.length);
  });

  it('Emit event with book item when fav button clicked', () => {
    const result = spyOn(component.onButton1, 'emit');
    component.button1(itemMock1);
    expect(result).toHaveBeenCalledOnceWith(itemMock1);
  });

  it('Emit event with book item when add button clicked', () => {
    const result = spyOn(component.onButton2, 'emit');
    component.button2(itemMock1);
    expect(result).toHaveBeenCalledOnceWith(itemMock1);
  });
})
