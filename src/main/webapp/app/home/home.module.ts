import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WebPageElementAnalyserSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { FieldsetModule } from 'primeng/fieldset';
import { PaginatorModule } from 'primeng/paginator';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
    imports: [
        WebPageElementAnalyserSharedModule,
        RouterModule.forChild([HOME_ROUTE]),
        TableModule,
        ButtonModule,
        InputTextModule,
        FieldsetModule,
        PaginatorModule,
        BrowserAnimationsModule
    ],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebPageElementAnalyserHomeModule {}
