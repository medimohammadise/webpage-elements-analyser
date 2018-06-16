import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage, LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { WebPageElementAnalyserSharedModule } from './shared';
import { WebPageElementAnalyserCoreModule } from './core';
import { WebPageElementAnalyserAppRoutingModule } from './app-routing.module';
import { WebPageElementAnalyserHomeModule } from './home/home.module';
import { WebPageElementAnalyserAccountModule } from './account/account.module';
import { WebPageElementAnalyserEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ErrorComponent } from './layouts';
import { WebPageAnalyseService } from './service/webpageanalyse.service';
import { CommonModule } from '@angular/common';

@NgModule({
    imports: [
        BrowserModule,
        WebPageElementAnalyserAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        WebPageElementAnalyserSharedModule,
        WebPageElementAnalyserCoreModule,
        WebPageElementAnalyserHomeModule,
        WebPageElementAnalyserAccountModule,
        WebPageElementAnalyserEntityModule,
        CommonModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
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
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
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
    bootstrap: [JhiMainComponent]
})
export class WebPageElementAnalyserAppModule {}
