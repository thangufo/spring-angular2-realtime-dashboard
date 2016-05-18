import { bootstrap } from '@angular/platform-browser-dynamic';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { Router,ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from '@angular/router';
import { AppComponent } from './app.component';
bootstrap(AppComponent,[ HTTP_PROVIDERS,ROUTER_PROVIDERS ]);