export type CompanyResponse = {
    content: Company[];
    totalPages: number;
}

export type Company = {
    id: number;
    name: string;
    description: string;

}
