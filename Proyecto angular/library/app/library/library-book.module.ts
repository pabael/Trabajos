import { NgModule } from "@angular/core";
import { BookListComponent } from "./components/library-book-list/library-book-list.component";
import { CommonModule} from "@angular/common";
import { MainPageComponent } from "./pages/library-main-page/library-main-page.component";
import { BooksService } from "./services/library-books.service";
import { BookDetailPageComponent } from "./pages/library-book-detail-page/library-book-detail-page.component";
import { RouterModule } from "@angular/router";
import { BookDetailComponent } from "./components/library-book-detail/library-book-detail.component";
import { HttpClientModule } from "@angular/common/http";
import { ReactiveFormsModule } from "@angular/forms";
import { SearchPageComponent } from "./pages/library-search-page/library-search-page.component";
import { AuthorDetailComponent } from "./components/library-author-detail/library-author-detail.component";
import { AuthorDetailPageComponent } from "./pages/library-author-detail-page/library-author-detail-page.component";
import { SearchComponent } from "./components/library-search/library-search.component";
import { CardComponent } from './components/library-card/library-card.component';
import { DefaultImagePipe } from "./pipes/default-image.pipe";

@NgModule({
  declarations: [
    BookListComponent,
    MainPageComponent,
    BookDetailComponent,
    BookDetailPageComponent,
    SearchComponent,
    SearchPageComponent,
    AuthorDetailComponent,
    AuthorDetailPageComponent,
    CardComponent,
    DefaultImagePipe
  ],
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  exports: [
    MainPageComponent,
    BookDetailPageComponent,
    AuthorDetailPageComponent,
    BookListComponent
  ],
  providers:[
    BooksService
  ]
})

export class BookModule{ }
