import type { PostResponse } from "../types/post-type";

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