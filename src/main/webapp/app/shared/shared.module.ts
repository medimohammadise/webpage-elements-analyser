import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { WebPageElementAnalyserSharedLibsModule, WebPageElementAnalyserSharedCommonModule } from './';

@NgModule({
    imports: [WebPageElementAnalyserSharedLibsModule, WebPageElementAnalyserSharedCommonModule],
    declarations: [],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [],
    exports: [WebPageElementAnalyserSharedCommonModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WebPageElementAnalyserSharedModule {}
