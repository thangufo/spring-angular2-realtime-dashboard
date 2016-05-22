import {Component,Input} from '@angular/core';
import {Http, HTTP_PROVIDERS,Headers} from '@angular/http';
import { Observable }     from 'rxjs/Observable';
import { Poll } from './domain/poll.domain';
import { PollChoice } from './domain/poll.choice.domain';

@Component({
    selector: 'poll-form',
    templateUrl: 'templates/poll.html'
})
export class PollComponent {
    @Input() poll : Poll;
    @Input() stompClient;
    selectedChoice : PollChoice;
    
    stompSelectChoiceCallback = (message) => {
        this.selectedChoice = JSON.parse(message.body);
    };
    
    constructor(public http: Http) {
    }
    
    ngOnInit() {
        //subscribe to the websocket
        this.stompClient.subscribe('/queue/selectChoice', this.stompSelectChoiceCallback);
    }
    
    selectChoice(pollChoice : PollChoice) {
        this.stompClient.send('/websocket/selectChoice',{},JSON.stringify(pollChoice));
    }
    
    submitPoll() {
        var headers : Headers = new Headers();
        headers.append('Content-Type', 'application/json');
        
        this.http.post("/api/poll/"+this.poll.id+"/submit",JSON.stringify(this.selectedChoice),{"headers": headers})
        .subscribe(
            res => {
                let data = res.json();
            }
        );    
    }
}