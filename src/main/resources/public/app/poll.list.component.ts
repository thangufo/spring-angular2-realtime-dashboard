import {Component,Input} from '@angular/core';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { Poll } from './domain/poll.domain';
import { PollComponent } from './poll.component';
 import { ROUTER_DIRECTIVES} from '@angular/router';

declare var SockJS;
declare var Stomp;

@Component({
    templateUrl: 'templates/poll.list.html',
    directives: [PollComponent, ROUTER_DIRECTIVES]
})
export class PollListComponent {
    polls: Poll[];
    currentPoll: Poll = null;
    stompClient : any;
    
    stompPollCallback = (message) => {
        this.polls = JSON.parse(message.body);
    };
    
    stompSelectPollCallback = (message) => {
        this.currentPoll = JSON.parse(message.body);
    };

    constructor(public http: Http) {      
        
    }
    
    ngOnInit() {
        this.http.get("/api/poll").subscribe(
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
        this.stompClient = Stomp.over(socket);
        let stompConnect = (frame) => {
            let whoami : any = frame.headers['user-name'];
            //subscribe to /user/queue/polls if you only want messages for the current user
            this.stompClient.subscribe('/queue/polls', this.stompPollCallback);
            this.stompClient.subscribe('/queue/selectPoll', this.stompSelectPollCallback);
        }
        
        this.stompClient.connect({}, stompConnect);
    }
    
    showPoll(poll : Poll) {
        //this.currentPoll = poll;
        this.stompClient.send('/websocket/selectPoll',{},JSON.stringify(poll));
    }
}