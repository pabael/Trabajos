import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientModule } from '@angular/common/http';
import { Card } from "../library-model/library-card";

import { ComponentFixture, TestBed } from "@angular/core/testing";
import { Router } from "@angular/router";
import { CardComponent } from "./library-card.component";
import { DefaultImagePipe } from "../../pipes/default-image.pipe";

describe('BookItem', () => {
  let router: Router;
  const itemMock1: Card ={
    olid: '1',
    url: '1',
    title: '1',
    imageUrl: '1',
    buttons: [{name:'fav', clicked: true}, {name:'add', clicked: true}]
  };

  let component: CardComponent;
  let fixture: ComponentFixture<CardComponent>;
  let element: HTMLElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientModule],
      declarations: [CardComponent, DefaultImagePipe]
    }).compileComponents();
    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(CardComponent);
    component = fixture.componentInstance;
    component.item = itemMock1;
    element = fixture.nativeElement;
    fixture.detectChanges();
  });

  it('Emit event with book item when fav button clicked', () => {

    const result = spyOn(component.onButton1, 'emit');
    const button = fixture.nativeElement.querySelector('.button1');
    button.click();
    expect(result).toHaveBeenCalledOnceWith(itemMock1);
  });

  it('Emit event with book item when add button clicked', () => {

    const result = spyOn(component.onButton2, 'emit');
    const button = fixture.nativeElement.querySelector('.button2');
    button.click();
    expect(result).toHaveBeenCalledOnceWith(itemMock1);
  });
})
