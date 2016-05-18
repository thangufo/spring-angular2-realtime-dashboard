import {Component,Input} from '@angular/core';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { Poll } from './domain/poll.domain';

@Component({
    templateUrl: 'templates/dashboard.html'
})
export class DashboardComponent {
    constructor(public http: Http) {
    }
    
    submitPoll() {
        
    }
}