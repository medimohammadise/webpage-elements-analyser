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
    webPageDcoumentMetaData: IWebPageDcoumentMetaData;
    hyperLinksHealthStatus: IHyperLinksHealthStatus;
    constructor(private webPageAnalyseService: WebPageAnalyseService, private eventManager: JhiEventManager) {}
    analyse() {
        console.log('analyse ...' + this.url);
        this.webPageAnalyseService.getWebPageMetaData(this.url).subscribe(
            data => {
                this.webPageDcoumentMetaData = data.body;
                console.log(this.webPageDcoumentMetaData);
            },
            error => {
                if (error.status === 503) {
                }
            }
        );

        this.webPageAnalyseService.gethyperLinksHealthData(this.url).subscribe(
            data => {
                this.hyperLinksHealthStatus = data.body;
                console.log(this.hyperLinksHealthStatus);
            },
            error => {
                if (error.status === 503) {
                }
            }
        );
    }
}
