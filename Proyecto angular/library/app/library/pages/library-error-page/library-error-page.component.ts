import { Component, Input } from '@angular/core';
import { BooksService } from '../../services/library-books.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-error-page',
  templateUrl: './library-error-page.component.html',
  styleUrls: ['./library-error-page.component.sass']
})
export class ErrorPageComponent {
  @Input() error: HttpErrorResponse = new HttpErrorResponse({});

  constructor(private booksService: BooksService) {
  }

  ngOnInit(): void {
    this.error = this.booksService.getError;
  }
}
