import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { WebPageElementAnalyserSharedModule } from '../shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import { adminState, LogsComponent, JhiConfigurationComponent, JhiDocsComponent } from './';

@NgModule({
    imports: [
        WebPageElementAnalyserSharedModule,
        RouterModule.forChild(adminState)
        /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    ],
    declarations: [LogsComponent, JhiConfigurationComponent, JhiDocsComponent],
    entryComponents: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebPageElementAnalyserAdminModule {}
