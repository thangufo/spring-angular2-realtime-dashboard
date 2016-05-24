import {Component,Input} from '@angular/core';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { PollStats } from './domain/pollStats.domain';
import { ChoiceStats } from './domain/choiceStats.domain';
import {nvD3} from 'ng2-nvd3'

declare let d3: any;
declare var SockJS;
declare var Stomp;

@Component({
    templateUrl: 'templates/dashboard.html',
    directives: [nvD3]
})
export class DashboardComponent {
    stompClient : any;
    stats: PollStats[];
    options;
    
    constructor(public http: Http) {
    }
    formatChartData = (stats) => {
        //we need to reformat the data a bit to use in pie chart
        for (var i=0;i< stats.length;i++){
            var stat : any = this.stats[i];
            stat.data = []
            for (var j=0;j< stat.choices.length;j++) {
                let choice : ChoiceStats = stat.choices[j];
                stat.data.push({
                    "key": choice.choice,
                    "y": choice.totalVote
                });
            }
        }
        console.log(stats);
    }
    stompAnswerSubmittedCallback = (message) => {
        this.stats = JSON.parse(message.body);
        this.formatChartData(this.stats);
    };
    
    ngOnInit() {
        this.options = {
            "chart": {
                type: 'pieChart',
                height: 500,
                x: function(d){return d.key;},
                y: function(d){return d.y;},
                showLabels: true,
                duration: 500,
                labelThreshold: 0.01,
                labelSunbeamLayout: true,
                legend: {
                    margin: {
                        top: 5,
                        right: 35,
                        bottom: 5,
                        left: 0
                    }
                },
            }
        }
        
        this.http.get("/api/poll/stats").subscribe(
            res => {
                this.stats = res.json();
                this.formatChartData(this.stats);
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