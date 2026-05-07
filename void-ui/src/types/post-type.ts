export type PostResponse = {
    id: number;
    title: string;
    content: string;
    authorId: number;
    crtTs: Date;
    lastUpdTs: Date;
};