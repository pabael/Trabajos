import { Component, EventEmitter, Output } from '@angular/core';
import { BooksService } from '../../services/library-books.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { BookDetail } from '../library-model/library-book-detail';
import { AuthorDetail } from '../library-model/library-auhtor-detail';
import { Card } from '../library-model/library-card';

@Component({
  selector: 'app-search',
  templateUrl: './library-search.component.html',
  styleUrl: './library-search.component.sass'
})
export class SearchComponent{
  searchForm: FormGroup;
  resultList: Card[] = [];
  searchType: string = 'book';

  @Output()
  public onSearching: EventEmitter<void> = new EventEmitter<void>();

  @Output()
  public onResultList: EventEmitter<Card[]> = new EventEmitter<Card[]>();

  constructor(private service: BooksService, private fb: FormBuilder, private router: Router){
    this.searchForm = this.fb.group({
      input: [''],
      option: 'title'
    });
    this.searchType = this.router.url.split('/search-')[1];
  }

  public showOptions(){
    return this.searchType === 'book';
  }

  onSubmit(): void{
    this.searching();

    this.resultList = [];
    const searchInput: string = this.searchForm.get('input')!.value;

    if(this.searchType === 'book') this.searchBook(searchInput);
    else if(this.searchType === 'author') this.searchAuthor(searchInput);
  }

  private searchBook(searchInput: string): void{

    const typeSearch: string = this.searchForm.get('option')!.value;

    let bookInList: BookDetail | undefined;
    if(typeSearch === 'title') bookInList = this.service.allBooks.find(book => book.title?.includes(searchInput));

    if(bookInList) this.resultList.push(this.service.bookToItem(bookInList));

    this.service.searchBooks(typeSearch, searchInput).subscribe({
      next: (listBookDetail: BookDetail[]) => {
        if(bookInList) listBookDetail.pop();

        listBookDetail.forEach(bookDetail =>{
          this.resultList.push(this.service.bookToItem(bookDetail));
          sessionStorage.setItem('searchList', JSON.stringify(this.resultList));

        })
        this.sendResultList();
      },
      error: (error:HttpErrorResponse) => {
        this.service.setError = error;
      }
    });
  }

  private searchAuthor(searchInput: string) :void{
    this.service.searchAuhtors(searchInput).subscribe({
      next: (authorList: AuthorDetail[]) => {
        authorList.forEach(author => this.resultList.push(this.service.authorToItem(author)));
        sessionStorage.setItem('searchList', JSON.stringify(this.resultList));
        this.sendResultList();
      },
      error: (error:HttpErrorResponse) => {
        this.service.setError = error;
      }
    })
  }

  private searching(): void{
    this.onSearching.emit();
  }

  private sendResultList():void{
    this.onResultList.emit(this.resultList);
  }

}
