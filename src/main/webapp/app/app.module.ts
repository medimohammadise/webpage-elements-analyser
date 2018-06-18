import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage, LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { WebPageElementAnalyserSharedModule } from './shared';
import { WebPageElementAnalyserCoreModule } from './core';
import { WebPageElementAnalyserAppRoutingModule } from './app-routing.module';
import { WebPageElementAnalyserHomeModule } from './home/home.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, PageRibbonComponent, ErrorComponent } from './layouts';
import { WebPageAnalyseService } from './service/webpageanalyse.service';
@NgModule({
    imports: [
        BrowserModule,
        WebPageElementAnalyserAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        WebPageElementAnalyserSharedModule,
        WebPageElementAnalyserCoreModule,
        WebPageElementAnalyserHomeModule
    ],
    declarations: [JhiMainComponent, ErrorComponent, PageRibbonComponent],
    providers: [
        WebPageAnalyseService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [LocalStorageService, SessionStorageService]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent],
    exports: []
})
export class WebPageElementAnalyserAppModule {}
