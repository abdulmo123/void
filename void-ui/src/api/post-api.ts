import type { CreatePost, PostResponse } from "../types/post-type";

const VOID_POST_BACKEND_URL = 'http://localhost:8082';

export async function getAllPosts(): Promise<PostResponse[]> {
    const response = await fetch(`${VOID_POST_BACKEND_URL}/api/v1/posts/all`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    });

    if (!response.ok) {
        throw new Error("Unable to fetch all posts...");
    }

    return response.json();
};

export async function createPost(token: string | null, createPostData: CreatePost): Promise<PostResponse> {
    const response = await fetch(`${VOID_POST_BACKEND_URL}/api/v1/posts/create`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(createPostData),
    });

    if (!response.ok) {
        throw new Error("Post creation failed!");
    }

    return response.json();
}