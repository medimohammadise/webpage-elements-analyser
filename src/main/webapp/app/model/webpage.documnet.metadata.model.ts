export interface IWebPageDcoumentMetaData {
    htmlVersion: String;
    title: String;
    numberOfInternalLinks: Number;
    numberOfExternalLinks: Number;
    hasLoginForm: boolean;
    loginAttemptSucessFull: boolean;
}

export class WebPageDcoumentMetaData implements IWebPageDcoumentMetaData {
    htmlVersion: String;
    title: String;
    numberOfInternalLinks: Number;
    numberOfExternalLinks: Number;
    hasLoginForm: boolean;
    loginAttemptSucessFull: boolean;
}
