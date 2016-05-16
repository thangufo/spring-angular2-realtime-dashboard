import { bootstrap } from '@angular/platform-browser-dynamic';
import {Http, HTTP_PROVIDERS} from '@angular/http';
import { AppComponent } from './app.component';
bootstrap(AppComponent,[ HTTP_PROVIDERS ]);