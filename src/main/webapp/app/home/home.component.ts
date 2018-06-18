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
    errorProcessingRequest: Boolean = false;
    errorMessage: String;
    webPageDcoumentMetaData: IWebPageDcoumentMetaData;
    hyperLinksHealthStatus: IHyperLinksHealthStatus;
    metaDataAnalyseInProgress: Boolean = false;
    hyperLinkHealthAnalysisInProgress: Boolean = false;
    ngOnInit() {}
    constructor(private webPageAnalyseService: WebPageAnalyseService, private eventManager: JhiEventManager) {}
    analyse() {
        this.webPageDcoumentMetaData = null;
        this.hyperLinksHealthStatus = null;
        this.metaDataAnalyseInProgress = true;
        this.errorProcessingRequest = false;
        this.webPageAnalyseService.getWebPageMetaData(this.url).subscribe(
            data => {
                this.webPageDcoumentMetaData = data.body;
                this.metaDataAnalyseInProgress = false;
            },
            error => {
                this.errorProcessingRequest = true;
                this.errorMessage = error.title;
                this.metaDataAnalyseInProgress = false;
            }
        );
        this.hyperLinkHealthAnalysisInProgress = true;
        this.webPageAnalyseService.gethyperLinksHealthData(this.url).subscribe(
            data => {
                this.hyperLinksHealthStatus = data.body;
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
