import {Component,Input} from '@angular/core';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { Observable }     from 'rxjs/Observable';
import { Poll } from './domain/poll.domain';

@Component({
    selector: 'poll-form',
    templateUrl: 'templates/poll.html'
})
export class PollComponent {
    @Input() poll : Poll;
    constructor(public http: Http) {
    }
    
    submitPoll() {
        
    }
}