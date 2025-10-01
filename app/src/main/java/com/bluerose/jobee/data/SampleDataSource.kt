package com.bluerose.jobee.data

import com.bluerose.jobee.R
import com.bluerose.jobee.data.models.Application
import com.bluerose.jobee.data.models.Chat
import com.bluerose.jobee.data.models.Company
import com.bluerose.jobee.data.models.Job
import com.bluerose.jobee.data.models.Notification

object SampleDataSource {
    val categories = listOf("Design", "Technology", "Finance", "Social Media")

    object Companies {
        val google = Company("Google LLC", R.drawable.logo_google, categories[1])
        val apple = Company("Apple Inc.", R.drawable.logo_apple, categories[1])
        val meta = Company("Meta Inc.", R.drawable.logo_meta, categories[1])
        val microsoft = Company("Microsoft", R.drawable.logo_microsoft, categories[1])
        val openai = Company("OpenAI", R.drawable.logo_openai, categories[1])
        val dropbox = Company("Dropbox", R.drawable.logo_dropbox, categories[1])
        val binance = Company("Binance", R.drawable.logo_binance, categories[2])
        val paypal = Company("Paypal Inc.", R.drawable.logo_paypal, categories[2])
        val github = Company("Github", R.drawable.logo_github, categories[3])
        val discord = Company("Discord", R.drawable.logo_discord, categories[3])
        val telegram = Company("Telegram", R.drawable.logo_telegram, categories[3])
        val tiktok = Company("Tiktok", R.drawable.logo_tiktok, categories[3])
        val spotify = Company("Spotify", R.drawable.logo_spotify, categories[3])
        val twitch = Company("Twitch", R.drawable.logo_twitch, categories[3])
        val x = Company("X Inc.", R.drawable.logo_x, categories[3])
        val reddit = Company("Reddit", R.drawable.logo_reddit, categories[3])
        val pinterest = Company("Pinterest", R.drawable.logo_pinterest, categories[0])
        val slack = Company("Slack", R.drawable.logo_slack, categories[0])
    }

    val jobs: List<Job> = listOf(
        Job(
            "e4a6a3b9-1f2d-4c8a-9e6a-7b3c9d1e0f2a",
            "Ui/Ux Designer",
            Companies.google,
            "California, United States",
            95000 to 140000,
            listOf("Full Time", "Onsite"),
            1725105600000
        ),
        Job(
            "f8b3c1d4-2e5f-4a9b-8c7d-6e4f1a2b3c4d",
            "Senior Software Engineer",
            Companies.apple,
            "Berlin, Germany",
            85000 to 120000,
            listOf("Full Time", "Hybrid"),
            1725185600000
        ),
        Job(
            "a2b9d4e1-3f6c-4b8d-9e5a-8c1d7f4b2a9e",
            "Product Manager",
            Companies.pinterest,
            "London, United Kingdom",
            90000 to 130000,
            listOf("Full Time", "Remote"),
            1725285600000
        ),
        Job(
            "c6d8e2f7-4a1b-4c9e-8b3d-9f2a7c5d1e8f",
            "Data Scientist",
            Companies.paypal,
            "New York, United States",
            110000 to 160000,
            listOf("Full Time", "Onsite", "Senior Level"),
            1725385600000
        ),
        Job(
            "b9e3f1a8-5c2d-4e8b-a7d6-1f4c8b3a9d2e",
            "Marketing Specialist",
            Companies.meta,
            "Paris, France",
            55000 to 75000,
            listOf("Contract", "Hybrid"),
            1725319000000
        ),
        Job(
            "d1e7f3c5-6b4a-4d9c-b2e8-a9f1d8c4b7a6",
            "Cloud Solutions Architect",
            Companies.openai,
            "Tokyo, Japan",
            120000 to 180000,
            listOf("Full Time", "Senior Level"),
            1725391000000
        ),
        Job(
            "f4c2a7b8-7e9d-4f6a-b8c3-d9e1f5a2b8d4",
            "Junior Frontend Developer",
            Companies.slack,
            "Austin, United States",
            70000 to 90000,
            listOf("Full Time", "Entry Level", "Onsite"),
            1725319000000
        ),
        Job(
            "a8b3e9d1-8f4c-4a7b-9d2e-f1c8b4a9d6e3",
            "HR Business Partner",
            Companies.microsoft,
            "Dublin, Ireland",
            65000 to 85000,
            listOf("Full Time", "Hybrid"),
            1725290000000
        ),
        Job(
            "c9d1e8f2-9a5b-4c8d-a3f7-e2b9c6d4a1f8",
            "Content Strategist",
            Companies.reddit,
            "Singapore, Singapore",
            60000 to 80000,
            listOf("Full Time", "Remote"),
            1725285600000
        ),
        Job(
            "e7f2a4b6-1c8d-4e9b-b5a3-d9c1f8b4e2a7",
            "DevOps Engineer",
            Companies.binance,
            "Amsterdam, Netherlands",
            75000 to 110000,
            listOf("Full Time", "Onsite"),
            1725365000000
        ),
        Job(
            "b3c8d7e4-2f9a-4b6d-8e1c-a5b9d2c7f4e1",
            "Financial Analyst",
            Companies.spotify,
            "Zurich, Switzerland",
            95000 to 125000,
            listOf("Full Time", "Hybrid"),
            1725345600000
        ),
        Job(
            "d9e1f4a2-3b8c-4d7e-9f6a-c2b8d5e1f7c3",
            "Lead Backend Developer",
            Companies.tiktok,
            "Bangalore, India",
            80000 to 130000,
            listOf("Full Time", "Remote", "Senior Level"),
            172529110000
        ),
        Job(
            "a5b9c2d7-4f1e-4a8b-b3d6-e8c4f9a1b2d5",
            "Quality Assurance Tester",
            Companies.telegram,
            "Toronto, Canada",
            60000 to 85000,
            listOf("Contract", "Onsite"),
            1725315600000
        ),
        Job(
            "c2b8d5e1-5a3f-4c9e-8b4d-f1e7a2b9c6d8",
            "Technical Program Manager",
            Companies.dropbox,
            "Seattle, United States",
            130000 to 175000,
            listOf("Full Time", "Hybrid"),
            1725309900000
        ),
        Job(
            "e8c4f9a1-6b2d-4e7f-a9c5-b3d8e1f4a7c9",
            "IT Support Specialist",
            Companies.google,
            "Sydney, Australia",
            55000 to 70000,
            listOf("Full Time", "Onsite"),
            1725307700000
        ),
        Job(
            "b3d8e1f4-7a9c-4f2e-b8d1-c5e7f2a9b4d3",
            "Recruiter",
            Companies.twitch,
            "Chicago, United States",
            65000 to 90000,
            listOf("Full Time", "Remote"),
            1725391800000
        ),
        Job(
            "d5e7f2a9-8b4d-4c1f-a3e9-b8d4c1f7a2e6",
            "Mobile Engineer (Android)",
            Companies.github,
            "Mountain View, United States",
            115000 to 165000,
            listOf("Full Time", "Onsite", "Mid Level"),
            1725700000000
        ),
        Job(
            "a9b4d3e8-9c1f-4a6b-b2d5-c8e1f4a7d3b9",
            "Machine Learning Engineer",
            Companies.openai,
            "Cambridge, United Kingdom",
            90000 to 140000,
            listOf("Full Time", "Hybrid", "Senior Level"),
            1725388800000
        ),
        Job(
            "c8e1f4a7-1d3b-4c9e-8a6f-d2b5c9e4f1a7",
            "Sales Executive",
            Companies.discord,
            "Madrid, Spain",
            50000 to 75000,
            listOf("Full Time", "Onsite"),
            1725323900000
        ),
        Job(
            "e2b5c9e4-2f1a-4d7b-9c3e-a8d6f2b1c4e9",
            "UX Researcher",
            Companies.google,
            "Copenhagen, Denmark",
            70000 to 95000,
            listOf("Full Time", "Remote"),
            1725300100000
        ),
        Job(
            "d6f2b1c4-3a8d-4e9f-b7c2-e1a9d4f8c3b6",
            "Database Administrator",
            Companies.paypal,
            "Warsaw, Poland",
            65000 to 90000,
            listOf("Full Time", "Hybrid"),
            1725331300000
        ),
        Job(
            "f1a9d4f8-4c3b-4a6e-8b9d-c7e2f1a5d8c3",
            "Graphic Designer",
            Companies.meta,
            "Los Angeles, United States",
            60000 to 85000,
            listOf("Contract", "Remote"),
            1725201100000
        ),
        Job(
            "a7e2f1a5-5d8c-4b3a-9e6f-d4c8e1f9a2b7",
            "Systems Analyst",
            Companies.apple,
            "Munich, Germany",
            70000 to 95000,
            listOf("Full Time", "Onsite"),
            1725201900000
        ),
        Job(
            "c4c8e1f9-6a2b-4d7e-a3f1-e9b5c2d8f4a1",
            "Operations Manager",
            Companies.x,
            "Dubai, UAE",
            90000 to 130000,
            listOf("Full Time", "Onsite", "Manager"),
            1725399900000
        ),
        Job(
            "e9b5c2d8-7f4a-4e1b-b8d3-f2a1c6e4d9b5",
            "Cybersecurity Analyst",
            Companies.spotify,
            "Washington D.C., United States",
            95000 to 140000,
            listOf("Full Time", "Hybrid"),
            1725338880000
        ),
        Job(
            "b2a1c6e4-8d9b-4f5a-a3e7-c6b9d2e1f8a4",
            "Business Development Manager",
            Companies.tiktok,
            "Sao Paulo, Brazil",
            75000 to 110000,
            listOf("Full Time", "Remote"),
            1725381900000
        ),
        Job(
            "d6b9d2e1-9f8a-4c3b-b8d4-e1f7a4c9b2e6",
            "Scrum Master",
            Companies.telegram,
            "Stockholm, Sweden",
            80000 to 105000,
            listOf("Contract", "Hybrid"),
            1725311100000
        ),
        Job(
            "a1f7a4c9-1b2e-4d8c-a6f3-b9e4d1f8a7c5",
            "Technical Writer",
            Companies.binance,
            "Helsinki, Finland",
            55000 to 75000,
            listOf("Part Time", "Remote"),
            1725301100000
        ),
        Job(
            "c9e4d1f8-2a7c-4b5d-b3e9-a8f6c2d1e4b9",
            "iOS Developer",
            Companies.pinterest,
            "Cupertino, United States",
            120000 to 170000,
            listOf("Full Time", "Onsite"),
            1725211100000
        ),
        Job(
            "e8f6c2d1-3e4b-4a9c-8d7b-b5c9e4f1a2d8",
            "Network Engineer",
            Companies.slack,
            "Seoul, South Korea",
            75000 to 100000,
            listOf("Full Time", "Onsite"),
            1725301000000
        ),
        Job(
            "b5c9e4f1-4a2d-4b8e-9f6c-c2d8e1f7a4b3",
            "Principal Software Engineer",
            Companies.microsoft,
            "Zurich, Switzerland",
            150000 to 220000,
            listOf("Full Time", "Hybrid", "Expert"),
            1725371900000
        ),
        Job(
            "d2d8e1f7-5a4b-4c3d-a8e9-b9c6f2d1e8a5",
            "Digital Marketing Manager",
            Companies.tiktok,
            "Lisbon, Portugal",
            60000 to 85000,
            listOf("Full Time", "Remote"),
            1725307100000
        ),
        Job(
            "a9c6f2d1-6e8a-4d9b-b5c3-e4d8f1a7c2b9",
            "Data Engineer",
            Companies.discord,
            "Tel Aviv, Israel",
            100000 to 145000,
            listOf("Full Time", "Onsite"),
            1725306400000
        ),
        Job(
            "c4d8f1a7-7c2b-4e6f-a9d8-b1e5c4f2a9d6",
            "Legal Counsel",
            Companies.openai,
            "Brussels, Belgium",
            95000 to 135000,
            listOf("Full Time", "Hybrid"),
            1725219900000
        ),
        Job(
            "b1e5c4f2-8a9d-4f1e-b6c3-d8e2f1a7c4b8",
            "Customer Success Manager",
            Companies.github,
            "Mexico City, Mexico",
            50000 to 70000,
            listOf("Full Time", "Remote"),
            1725356500000
        ),
        Job(
            "d8e2f1a7-9c4b-4a8d-a3f9-e5c1f8b4d2e7",
            "Research Scientist",
            Companies.reddit,
            "Taipei, Taiwan",
            110000 to 160000,
            listOf("Full Time", "Onsite", "PhD Required"),
            1725380000000
        ),
        Job(
            "a5c1f8b4-1d2e-4b9c-b8e6-f2a9d1c7e4b3",
            "Automation Engineer",
            Companies.google,
            "Prague, Czech Republic",
            65000 to 90000,
            listOf("Full Time", "Hybrid"),
            1725359900000
        ),
        Job(
            "c2a9d1c7-2e4b-4c6d-a3f1-b8e5c4f2a9d8",
            "Cloud Security Engineer",
            Companies.paypal,
            "Reston, United States",
            125000 to 170000,
            listOf("Full Time", "Remote"),
            1725239900000
        ),
        Job(
            "b8e5c4f2-3a9d-4f1e-b6c3-d1e8f7a4c2b5",
            "Video Producer",
            Companies.twitch,
            "Los Angeles, United States",
            70000 to 95000,
            listOf("Contract", "Onsite"),
            1725255900000
        ),
        Job(
            "d1e8f7a4-4c2b-4a8d-a3f9-e7c5f2b1d8e4",
            "Game Developer",
            Companies.dropbox,
            "Montreal, Canada",
            75000 to 115000,
            listOf("Full Time", "Hybrid"),
            1725201900000
        ),
        Job(
            "a7c5f2b1-5d8e-4b9c-b6e2-f1a9d4c8e7b3",
            "UX Writer",
            Companies.x,
            "San Francisco, United States",
            85000 to 120000,
            listOf("Full Time", "Remote"),
            1725388800000
        ),
        Job(
            "c1a9d4c8-6e7b-4c3d-a8f9-b4e2f1a7d6c5",
            "Systems Administrator",
            Companies.meta,
            "Frankfurt, Germany",
            60000 to 80000,
            listOf("Full Time", "Onsite"),
            1725355500000
        ),
        Job(
            "b4e2f1a7-7d6c-4f8e-b3a1-c9e8f4d2a1b7",
            "Solutions Consultant",
            Companies.spotify,
            "Milan, Italy",
            65000 to 90000,
            listOf("Full Time", "Hybrid"),
            1725344400000
        ),
        Job(
            "d9e8f4d2-8a1b-4c5f-a8e3-b2c6f1a9d4e8",
            "Public Relations Specialist",
            Companies.pinterest,
            "New York, United States",
            70000 to 95000,
            listOf("Full Time", "Hybrid"),
            1725299900000
        ),
        Job(
            "a2c6f1a9-9d4e-4b8a-b3f7-c8e1f4a2d6b9",
            "Ethical Hacker",
            Companies.openai,
            "London, United Kingdom",
            90000 to 130000,
            listOf("Contract", "Remote"),
            1725345200000
        ),
        Job(
            "c8e1f4a2-1d6b-4c9f-a8e3-b5d9f2a7c4e1",
            "Data Privacy Officer",
            Companies.dropbox,
            "Dublin, Ireland",
            85000 to 115000,
            listOf("Full Time", "Onsite"),
            1725105200000
        ),
        Job(
            "b5d9f2a7-2c4e-4b1a-b3f8-d1e8f7a4c2b6",
            "Firmware Engineer",
            Companies.google,
            "Shenzhen, China",
            80000 to 125000,
            listOf("Full Time", "Onsite"),
            1725355340000
        ),
        Job(
            "d1e8f7a4-3c2b-4a8d-a9f6-e7c5f2b1d8e4",
            "Agile Coach",
            Companies.microsoft,
            "Vienna, Austria",
            85000 to 110000,
            listOf("Full Time", "Hybrid"),
            1725277900000
        ),
        Job(
            "a7c5f2b1-4d8e-4b9c-b6e2-f1a9d4c8e7b3",
            "Corporate Trainer",
            Companies.binance,
            "Oslo, Norway",
            70000 to 90000,
            listOf("Contract", "Onsite"),
            1725212100000
        ),
        Job(
            "c1a9d4c8-5e7b-4c3d-a8f9-b4e2f1a7d6c5",
            "AI Research Intern",
            Companies.tiktok,
            "Palo Alto, United States",
            45000 to 60000,
            listOf("Internship", "Onsite"),
            1725207000000
        )
    )
    val applications: List<Application> = listOf(
        Application(jobs[0], Application.Stage.Sent),
        Application(jobs[1], Application.Stage.Pending),
        Application(jobs[2], Application.Stage.Rejected),
        Application(jobs[3], Application.Stage.Accepted),
        Application(jobs[4], Application.Stage.Sent),
    )
    val chats: List<Chat> = listOf(
        Chat(
            Companies.google,
            "Don’t forget we have a meeting scheduled today at 3 PM.",
            1758091212000,
            2
        ),
        Chat(
            Companies.pinterest,
            "Your package has been delivered successfully to your doorstep.",
            1758087211000,
            0
        ),
        Chat(
            Companies.apple,
            "The project documentation has been updated, please review it.",
            1758083210000,
            5
        ),
        Chat(
            Companies.microsoft,
            "Are we still on for movie night this weekend?",
            1758079215000,
            0
        ),
        Chat(
            Companies.openai,
            "I just made a new playlist, you should check it out!",
            1758075212000,
            1
        ),
        Chat(
            Companies.meta,
            "Here’s the link I mentioned earlier, let me know your thoughts.",
            1758071219000,
            3
        ),
        Chat(
            Companies.tiktok,
            "Looking forward to seeing you at the event tomorrow.",
            1758067213000,
            0
        ),
        Chat(
            Companies.telegram,
            "The quarterly report has been published, please take a look.",
            1758063218000,
            4
        ),
        Chat(
            Companies.github,
            "Thanks for the update, I’ll get back to you soon.",
            1758059216000,
            0
        ),
        Chat(
            Companies.spotify,
            "The new feature update has been deployed successfully.",
            1758055214000,
            7
        ),
    )
    val notifications: List<Notification> = listOf(
        Notification(
            "New Updates Available!",
            "Update Jobee now to get access to the latest features and ease of applying for jobs.",
            1725105600000,
            true
        ),
        Notification(
            "Application Submitted",
            "Your application for Frontend Developer at Google LLC has been successfully submitted.",
            1728192000000,
            true
        ),
        Notification(
            "Interview Scheduled",
            "Your interview for Backend Developer at Amazon is scheduled for September 25, 2025.",
            1758739200000,
            true
        ),
        Notification(
            "New Job Recommendation",
            "We found a new UI/UX Designer position in Berlin that matches your profile.",
            1730428800000,
            false
        ),
        Notification(
            "Job Alert: Data Scientist",
            "Microsoft is hiring Data Scientists in London. Apply now before September 30, 2025.",
            1759209600000,
            true
        ),
        Notification(
            "Profile Viewed",
            "A recruiter from Apple viewed your profile yesterday.",
            1735689600000,
            false
        ),
        Notification(
            "New Message",
            "You received a new message from HR at Tesla.",
            1740787200000,
            false
        ),
        Notification(
            "Application Update",
            "Your application for Mobile Developer at Spotify has been shortlisted.",
            1745961600000,
            false
        ),
        Notification(
            "Offer Received",
            "Congratulations! You have received an offer from Meta.",
            1748640000000,
            false
        ),
        Notification(
            "Event Reminder",
            "Don’t forget: Online Career Fair starts tomorrow at 10:00 AM.",
            1753747200000,
            false
        ),
        Notification(
            "Skill Badge Earned",
            "You earned a new skill badge in Machine Learning on your profile.",
            1738368000000,
            false
        ),
        Notification(
            "Security Notice",
            "A new login to your account was detected from New York, United States.",
            1743379200000,
            true
        ),
        Notification(
            "Subscription Expiring",
            "Your premium subscription will expire in 5 days. Renew now to continue benefits.",
            1747267200000,
            true
        ),
        Notification(
            "Resume Updated",
            "Your resume was successfully updated and is now visible to recruiters.",
            1726704000000,
            false
        ),
        Notification(
            "Weekly Job Digest",
            "Here are 15 new jobs this week that match your saved preferences.",
            1751500800000,
            false
        )
    )
}
