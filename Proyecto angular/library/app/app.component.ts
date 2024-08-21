import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.sass'
})

export class AppComponent {
  title = 'library';

  previousUrl: string = '';

  constructor(private router: Router) {
    router.events
    .pipe(filter((evento): evento is NavigationEnd => evento instanceof NavigationEnd))
    .subscribe(
      (event: NavigationEnd) => {
        if(((this.previousUrl !== '/search-book' && event.url !== '/search-book')
          && (this.previousUrl !== '/search-author' && event.url !== '/search-author'))
        || (this.previousUrl === '/search-book' && event.url === '/search-author')
        || (this.previousUrl === '/search-author' && event.url === '/search-book'))
        {
          sessionStorage.removeItem('searchList');
        }
        this.previousUrl = event.url;
    });
  }

  reloadSearch() {
    if(this.previousUrl === '/search-book')  {
      sessionStorage.removeItem('searchList');
      window.location.reload();
    }
  }

}
