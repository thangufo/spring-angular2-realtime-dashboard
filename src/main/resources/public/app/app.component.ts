import {Component} from '@angular/core';

@Component({
    selector: 'my-app',
    templateUrl: 'templates/index.html'
})
export class AppComponent {
    name: string;
    names: string[];

    constructor() {
        this.name = "Thang Nguyen 123";
        this.names = ['Ari', 'Carlos', 'Felipe', 'Nate'];
    }
}