import {Component} from '@angular/core';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { Poll } from './domain/poll.domain';
import { PollListComponent } from './poll.list.component';
import { DashboardComponent } from './dashboard.component';
import { Router,ROUTER_DIRECTIVES, Routes } from '@angular/router';

@Component({
    selector: 'my-app',
    templateUrl: 'templates/index.html',
    directives: [ROUTER_DIRECTIVES]
})
@Routes([
  {path: '/dashboard', component: DashboardComponent},
  {path: '/poll', component: PollListComponent},
  {path: '/',        component: PollListComponent}
])
export class AppComponent {
    constructor(public http: Http,private router: Router) {
        
    }
}