import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { WebPageAnalyseService } from '../service/webpageanalyse.service';
import { IWebPageDcoumentMetaData } from '../model/webpage.documnet.metadata.model';
import { IHyperLinksHealthStatus } from '../model/hyperlink.health.status';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    url: string;
    errorProcessingRequest: boolean = false;
    errorMessage: String;
    webPageDcoumentMetaData: IWebPageDcoumentMetaData;
    hyperLinksHealthStatus: IHyperLinksHealthStatus;
    metaDataAnalyseInProgress: boolean = false;
    hyperLinkHealthAnalysisInProgress: boolean = false;
    constructor(private webPageAnalyseService: WebPageAnalyseService, private eventManager: JhiEventManager) {}
    analyse() {
        this.webPageDcoumentMetaData = null;
        this.hyperLinksHealthStatus = null;
        this.metaDataAnalyseInProgress = true;
        this.webPageAnalyseService.getWebPageMetaData(this.url).subscribe(
            data => {
                this.webPageDcoumentMetaData = data.body;
                this.metaDataAnalyseInProgress = false;
            },
            error => {
                this.errorProcessingRequest = true;
                this.errorMessage = error.title;
                this.metaDataAnalyseInProgress = false;
                console.log('metaDataAnalyseInProgress ' + this.metaDataAnalyseInProgress);
            }
        );
        this.hyperLinkHealthAnalysisInProgress = true;
        this.webPageAnalyseService.gethyperLinksHealthData(this.url).subscribe(
            data => {
                this.hyperLinksHealthStatus = data.body;
                console.log(this.hyperLinksHealthStatus);
                this.hyperLinkHealthAnalysisInProgress = false;
            },
            error => {
                this.errorProcessingRequest = true;
                this.errorMessage = error.title;
                this.hyperLinkHealthAnalysisInProgress = false;
            }
        );
    }
}
