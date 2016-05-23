import {Component,Input} from '@angular/core';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { Poll } from './domain/poll.domain';

declare var SockJS;
declare var Stomp;

@Component({
    templateUrl: 'templates/dashboard.html'
})
export class DashboardComponent {
    stompClient : any;
    polls: Poll[];
    
    constructor(public http: Http) {
    }
    stompAnswerSubmittedCallback = (message) => {
        
    };
    
    ngOnInit() {
        this.http.get("/api/poll").subscribe(
            res => {
                this.polls = res.json();
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
        this.stompClient = Stomp.over(socket);
        let stompConnect = (frame) => {
            //subscribe to /user/queue/polls if you only want messages for the current user
            this.stompClient.subscribe('/queue/answerSubmitted', this.stompAnswerSubmittedCallback);
        }
        
        this.stompClient.connect({}, stompConnect);
    }
}