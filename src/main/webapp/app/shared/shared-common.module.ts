import { NgModule } from '@angular/core';

import { WebPageElementAnalyserSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [WebPageElementAnalyserSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [WebPageElementAnalyserSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class WebPageElementAnalyserSharedCommonModule {}
