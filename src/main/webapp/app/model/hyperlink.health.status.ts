export interface IHyperLinksHealthStatus {
    url: String;
    https: boolean;
    httpStatus: Number;
    redirect: boolean;
    redirectURL: String;
    resourceException: String;
}
export class HyperLinksHealthStatus implements IHyperLinksHealthStatus {
    url: String;
    https: boolean;
    httpStatus: Number;
    redirect: boolean;
    redirectURL: String;
    resourceException: String;
}
