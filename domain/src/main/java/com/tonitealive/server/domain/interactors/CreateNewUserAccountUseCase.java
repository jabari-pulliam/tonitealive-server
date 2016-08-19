package com.tonitealive.server.domain.interactors;

import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class CreateNewUserAccountUseCase {

    private final UserProfilesRepository userProfilesRepository;

    @Autowired
    public CreateNewUserAccountUseCase(UserProfilesRepository userProfilesRepository) {
        this.userProfilesRepository = userProfilesRepository;
    }

    public Observable<Void> execute(String username, String email) {
        checkNotNull(username);
        checkNotNull(email);
        checkArgument(!username.isEmpty());
        checkArgument(!email.isEmpty());

        return Observable.create(subscriber -> {
            UserProfile profile = UserProfile.create(username, email, null);
            userProfilesRepository.save(profile);

            if (!subscriber.isUnsubscribed())
                subscriber.onCompleted();
        });
    }

}
