import { useEffect, useState } from "react";
import {
    Container,
    Text,
    Textarea,
    Button,
    Stack,
    Group,
    Avatar,
    Paper,
    Divider,
    ActionIcon,
    useMantineColorScheme,
    TextInput,
} from "@mantine/core";
import { IconArrowRight, IconHeart, IconMessageCircle, IconSearch, IconShare } from "@tabler/icons-react";
import { createPost, getAllPosts } from "../api/post-api";
import type { CreatePost, PostResponse } from "../types/post-type";
import { theme } from "../theme";
import { useAuth } from "../context/AuthContext";

// Mock posts for now — replace with real API call later
const posts = await getAllPosts();
console.log('posts ... ', posts);

function PostCard({ post, dark }: { post: PostResponse; dark: boolean }) {
    const [liked, setLiked] = useState(false);

    return (
        <Paper
            radius="sm"
            p="xl"
            style={{
                backgroundColor: dark ? "#111111" : "#ffffff",
                border: `1px solid ${dark ? "#1e1e1e" : "#e8e6e0"}`,
                transition: "border-color 0.2s ease",
            }}
        >
            <Group mb="md" gap="sm">
                <Avatar
                    radius="xl"
                    size="sm"
                    style={{
                        backgroundColor: dark ? "#222222" : "#dddcda",
                        fontFamily: "Courier New, monospace",
                        fontSize: "11px",
                    }}
                >
                    {post.authorUsername ? post.authorUsername[0].toUpperCase() : '?'}
                </Avatar>
                <div>
                    <Text style={{ fontFamily: "Courier New, monospace", fontSize: "12px", color: dark ? "#dddddd" : "#0a0a0a", fontWeight: 500 }}>
                        {post.authorUsername}
                    </Text>
                    <Text style={{ fontFamily: "Courier New, monospace", fontSize: "10px", color: "#888888", letterSpacing: "0.5px" }}>
                        {new Date(post.crtTs).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' })}
                    </Text>
                </div>
            </Group>

            <Text style={{ fontFamily: "Georgia, serif", fontSize: "18px", color: dark ? "#f5f4f0" : "#0a0a0a", marginBottom: "10px", lineHeight: 1.3 }}>
                {post.title}
            </Text>

            <Text style={{ fontFamily: "Courier New, monospace", fontSize: "13px", color: dark ? "#888888" : "#555555", lineHeight: 1.8, marginBottom: "20px" }}>
                {post.content}
            </Text>

            <Divider color={dark ? "#1e1e1e" : "#e8e6e0"} mb="md" />

            <Group gap="md">
                <ActionIcon variant="subtle" color={liked ? "red" : "gray"} onClick={() => setLiked(!liked)} size="sm">
                    <IconHeart size={15} fill={liked ? "currentColor" : "none"} />
                </ActionIcon>
                <ActionIcon variant="subtle" color="gray" size="sm">
                    <IconMessageCircle size={15} />
                </ActionIcon>
                <ActionIcon variant="subtle" color="gray" size="sm">
                    <IconShare size={15} />
                </ActionIcon>
            </Group>
        </Paper>
    );
}

export default function HomePage() {
    const { colorScheme } = useMantineColorScheme();
    const dark = colorScheme === "dark";
    const [postContent, setPostContent] = useState("");
    const [postTitle, setPostTitle] = useState("");
    const [posts, setPosts] = useState<PostResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const { token } = useAuth();

    useEffect(() => {
        const fetchPosts = async () => {
            try {
                const data = await getAllPosts();
                setPosts(data);
            } catch (error) {
                console.error("Failed to fetch posts", error);
            } finally {
                setLoading(false);
            }
        };
        fetchPosts();
    }, []);

    const handlePost = async () => {
        try {
            if (!postContent.trim()) return;

            if (!token) {
                alert("No token found! Re-login to generate a new token!");
                return;
            }

            const response: CreatePost = await createPost(token, {
                title: postTitle,
                content: postContent
            });

            console.log('response ... ', response);
            console.log("Post submitted:", { postTitle, postContent });

        } catch (error) {
            console.error(error);
        } finally {
            setPostTitle("");
            setPostContent("");
        }
    };

    return (
        <div style={{ backgroundColor: dark ? "#0a0a0a" : "#f5f4f0", minHeight: "100vh", paddingTop: "40px", paddingBottom: "80px" }}>
            <Container size="lg">
                <TextInput
                    radius="md"
                    size="md"
                    mb={30}
                    placeholder="Search post"
                    rightSectionWidth={42}
                    leftSection={<IconSearch size={18} stroke={1.5} />}
                    rightSection={
                        <ActionIcon size={32} radius="xl" color={theme.primaryColor} variant="filled">
                            <IconArrowRight size={18} stroke={1.5} />
                        </ActionIcon>
                    }
                />
                <Stack gap="lg">

                    <div>
                        <Text style={{ fontFamily: "Courier New, monospace", fontSize: "10px", letterSpacing: "3px", textTransform: "uppercase", color: "#888888", marginBottom: "6px" }}>
                            The Feed
                        </Text>
                        <Text style={{ fontFamily: "Georgia, serif", fontSize: "28px", color: dark ? "#f5f4f0" : "#0a0a0a" }}>
                            What's in the void today<span style={{ color: "#888888" }}>.</span>
                        </Text>
                    </div>

                    {/* Composer */}
                    <Paper radius="sm" p="lg" style={{ backgroundColor: dark ? "#111111" : "#ffffff", border: `1px solid ${dark ? "#1e1e1e" : "#e8e6e0"}` }}>
                        <input
                            placeholder="Title (optional)"
                            value={postTitle}
                            onChange={(e) => setPostTitle(e.target.value)}
                            style={{
                                width: "100%", background: "none", border: "none", outline: "none",
                                fontFamily: "Georgia, serif", fontSize: "16px",
                                color: dark ? "#f5f4f0" : "#0a0a0a", marginBottom: "10px",
                            }}
                        />
                        <Divider color={dark ? "#1e1e1e" : "#e8e6e0"} mb="sm" />
                        <Textarea
                            placeholder="Put something into the void..."
                            value={postContent}
                            onChange={(e) => setPostContent(e.currentTarget.value)}
                            minRows={3}
                            autosize
                            variant="unstyled"
                            styles={{
                                input: {
                                    fontFamily: "Courier New, monospace", fontSize: "13px",
                                    color: dark ? "#888888" : "#555555", lineHeight: 1.8, padding: 0,
                                },
                            }}
                        />
                        <Group justify="flex-end" mt="md">
                            <Button
                                onClick={handlePost}
                                disabled={!postContent.trim()}
                                color="dark"
                                size="xs"
                                style={{ fontFamily: "Courier New, monospace", letterSpacing: "2px", fontSize: "10px" }}
                            >
                                POST
                            </Button>
                        </Group>
                    </Paper>

                    <Divider
                        color={dark ? "#1e1e1e" : "#e8e6e0"}
                        label={
                            <Text style={{ fontFamily: "Courier New, monospace", fontSize: "10px", color: "#888888", letterSpacing: "2px" }}>
                                RECENT POSTS
                            </Text>
                        }
                        labelPosition="left"
                    />

                    {/* Feed */}
                    {loading ? (
                        <Text style={{ fontFamily: "Courier New, monospace", fontSize: "12px", color: "#888888", textAlign: "center" }}>
                            Loading...
                        </Text>
                    ) : posts.length === 0 ? (
                        <Text style={{ fontFamily: "Courier New, monospace", fontSize: "12px", color: "#888888", textAlign: "center" }}>
                            Nothing in the void yet. Be the first.
                        </Text>
                    ) : (
                        <Stack gap="md">
                            {posts.map((post) => (
                                <PostCard key={post.id} post={post} dark={dark} />
                            ))}
                        </Stack>
                    )}

                </Stack>
            </Container>
        </div>
    );
}