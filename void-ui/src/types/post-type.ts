export type PostResponse = {
    id: number;
    title: string;
    content: string;
    authorId: number;
    authorUsername: string;
    crtTs: Date;
    lastUpdTs: Date;
};

export type CreatePost = {
    title: string;
    content: string;
}