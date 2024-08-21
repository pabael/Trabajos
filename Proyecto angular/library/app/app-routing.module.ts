import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './library/pages/library-main-page/library-main-page.component';
import { BookDetailPageComponent } from './library/pages/library-book-detail-page/library-book-detail-page.component';
import { ErrorPageComponent } from './library/pages/library-error-page/library-error-page.component';
import { SearchPageComponent } from './library/pages/library-search-page/library-search-page.component';
import { AuthorDetailPageComponent } from './library/pages/library-author-detail-page/library-author-detail-page.component';

const routes: Routes = [
  {
    path: 'book/:olid',
    component: BookDetailPageComponent
  },
  {
    path: 'work/:olid',
    component: BookDetailPageComponent
  },
  {
    path: 'error',
    component: ErrorPageComponent
  },
  {
    path: 'main',
    component: MainPageComponent
  },
  {
    path: 'search-book',
    component: SearchPageComponent
  },
  {
    path: 'search-author',
    component: SearchPageComponent
  },
  {
    path: 'author/:olid',
    component: AuthorDetailPageComponent
  },
  {
    path:'**',
    redirectTo: 'search-book'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
