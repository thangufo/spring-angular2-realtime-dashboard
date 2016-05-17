import {Component} from '@angular/core';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { Observable }     from 'rxjs/Observable';
import { Poll } from './domain/poll.domain';
import { PollComponent } from './poll.component';

declare var SockJS;
declare var Stomp;

@Component({
    selector: 'my-app',
    templateUrl: 'templates/index.html',
    directives: [PollComponent]
})
export class AppComponent {
    polls: Poll[];
    currentPoll: Poll = null;
    
    stompPollCallback = (message) => {
        this.polls = JSON.parse(message.body);
    };
    
    constructor(public http: Http) {      
        this.http.get("/poll").subscribe(
            res => {
                let data = res.json();
                this.polls = data;
            },
            err => {
            }
        );
        
        //subscribe to the websocket
        this.connect();
    }
    
    /**
     * Connect to SpringBoot Websocket
     */
    connect() {
        let socket : any = new SockJS('/message');
        let stompClient : any = Stomp.over(socket);
        let stompConnect = (frame) => {
            let whoami : any = frame.headers['user-name'];
            //subscribe to /user/queue/polls if you only want messages for the current user
            stompClient.subscribe('/queue/polls', this.stompPollCallback);
        }
        
        stompClient.connect({}, stompConnect);
    }
    
    removePoll(pollId: number) {
        this.http.delete("/poll/"+pollId).subscribe(
            res => {
            },
            err => {
            }
        );
    }
    
    showPoll(poll : Poll) {
        this.currentPoll = poll;
        console.log("here");
    }
}