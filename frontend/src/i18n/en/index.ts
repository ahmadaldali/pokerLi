import { localeRoutes } from "../routes";
import type { BaseTranslation } from "../i18n-types";

const AdminPagesTranslation = {
  sprints: {
    new: {
      title: "Create New Sprint",
      name: "Sprint Name",
      sequence: "Estimation Sequence (comma separated)",
      submit: "Create Sprint",
    },
  },
};

const PagesTranslation = {
  admin: AdminPagesTranslation,
  auth: {
    login: {
      title: "Sign in to your account",
      email: "Email address",
      password: "Password",
      submit: "Sign In",
      noAccount: "Don't have an account?",
      signUp: "Sign up",
    },
    signUp: {
      title: "Create your account",
      name: "Full Name",
      email: "Email address",
      password: "Password",
      confirmPassword: "Confirm Password",
      submit: "Sign Up",
      haveAccount: "Already have an account?",
      signIn: "Sign in",
    },
    logout: "logout",
  },
  sprints: {
    createNewSprint: "create a new sprint",
  },
  sprint: {
    userStories: "User stories",
    activeVoting: "Active voting",
    selectUserStoryToBeginVoting: "Select a user story to begin voting",
  },
};

const ErrorsTranslation = {
  // API Errors
  INVALID_CREDENTIALS: "Invalid email or password",
  INTERNAL_SERVER_ERROR:
    "An internal server error occurred. Please try again later",
  NOT_FOUND: "The requested resource was not found",
  UN_AUTHORIZED: "You are not authorized to perform this action",
  ACTION_NOT_ALLOWED: "This action is not allowed",
  BAD_REQUEST: "Bad request. Please check your input",
  NAME_EXIST: "Name already exists",

  //
  required: "{field} is required",
  min_length: "{field} must be at least {min} characters long",
  max_length: "{field} must be at most {max} characters long",
  one_uppercase: "{field} must contain at least one uppercase letter",
  one_lowercase: "{field} must contain at least one lowercase letter",
  one_number: "{field} must contain at least one number",
  one_special: "{field} must contain at least one special character",
  password_mismatch: "Passwords do not match",
  invalid_email: "Invalid email address",
  sequence_invalid_numbers: "Estimation sequence must contain only numbers",
  sequence_max_items: "Estimation sequence can have at most 10 items",

  // error messages
  errorLoadingSprint: "Error loading sprint.",
};

const BlocksTranslation = {
  header: {
    sprints: "Sprints",
    logout: "Logout",
    profile: "Profile",
    login: "Login",
    getStarted: "Get started",
  },
  footer: {
    text: "Simple, collaborative estimation for agile teams. Create rooms, vote effortlessly, and reach consensus faster.",
    navigation: {
      title: "Navigation",
      home: "Home",
      sprints: "Sprints",
      login: "Login",
      register: "Register",
    },
    legal: {
      title: "Legal",
      privacyPolicy: "Privacy Policy",
      termsOfService: "Terms of Service",
    },
    social: {
      title: "Follow Us",
      linkedIn: "LinkedIn",
    },
  },
  sprints: {
    joined: "Sprints you’ve joined",
    joinable: "Sprints you can join",
    header: {
      inProgress: "Voting in progress",
      noActiveVoting: "No active voting",
      userStories: "user stories",
      revealedStories: "stories revealed",
      membersInSprint: "members in sprint",
      uniqueVoters: "unique voters",
      createdBy: "Created by",
      sequence: "Sequence:",
    },
  },
  emptyUserStory: {
    title: "No user stories yet",
    description:
      "Start a new voting session to estimate your first story.",
  },
  userStory: {
    votingNow: "Voting now",
    voteAgain: "Vote again",
    voteThisStory: "Vote this story",
  },
  userStoryResults: {
    votesCast: "Votes",
    averageEstimate: "Average estimate",
  },
  reveal: {
    title: "Reveal Votes",
    text: "Pick your cards",
    chooseNextUserStory: "Choose the next user story to estimate",
  },
  startNewVoting: {
    startNewVoting: "Start New Voting",
  }

};

const FieldsTranslation = {
  name: {
    label: "Name",
    placeholder: "Ahmad Aldali",
  },
  email: {
    label: "Email",
    placeholder: "dev@ahmad.me",
  },
  password: {
    label: "Password",
    placeholder: "••••••••",
  },
  confirmPassword: {
    label: "Confirm Password",
    placeholder: "••••••••",
  },
  sprintName: {
    label: "Sprint Name",
    placeholder: "Sprint 1",
  },
  sequence: {
    label: "Estimation Sequence",
    placeholder: "e.g., 1,2,3,5,8,13,21",
  },
};

const ModalsTranslation = {
  createGuest: {
    title: "Join as a Guest",
    login: "Login",
    signUp: "Sign up",
    submit: "Join",
  },
};

const TablesTranslation = {
  default: {
    actions: "Actions",
    noData: "No data available",
  },
  sprints: {
    empty: "No Sprints found",
    columns: {
      name: "Sprint",
      creator: "Creator",
      sequence: "Sequence",
    },
    actions: {
      title: "Actions",
      join: "Join",
      view: "View",
    },
  },
};

const en: BaseTranslation = {
  routes: localeRoutes("en"),
  pages: PagesTranslation,
  errors: ErrorsTranslation,
  blocks: BlocksTranslation,
  fields: FieldsTranslation,
  modals: ModalsTranslation,
  tables: TablesTranslation
};

export default en;
