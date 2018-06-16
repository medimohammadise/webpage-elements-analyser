import { Injectable } from '@angular/core';
import { SERVER_API_URL } from '../app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { IWebPageDcoumentMetaData } from '../model/webpage.documnet.metadata.model';
import { Observable, of } from 'rxjs';
import { IHyperLinksHealthStatus } from '../model/hyperlink.health.status';

@Injectable()
export class WebPageAnalyseService {
    private webPageMetaDataResourceUrl = SERVER_API_URL + 'api/webPageMetaData';
    private hyperLinksHealthResourceUrl = SERVER_API_URL + 'api/hyperLinksHealth';
    constructor(private http: HttpClient) {}
    getWebPageMetaData(url: string): Observable<HttpResponse<IWebPageDcoumentMetaData>> {
        return this.http.get<IWebPageDcoumentMetaData>(`${this.webPageMetaDataResourceUrl}?url=${url}`, { observe: 'response' });
    }
    gethyperLinksHealthData(url: string): Observable<HttpResponse<IHyperLinksHealthStatus>> {
        return this.http.get<IHyperLinksHealthStatus>(`${this.hyperLinksHealthResourceUrl}?url=${url}`, { observe: 'response' });
    }
}
